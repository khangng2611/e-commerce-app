package com.khangng.payment_service.payment;

import com.khangng.payment_service.customer.CustomerResponse;
import com.khangng.payment_service.entity.Payment;
import com.khangng.payment_service.kafka.PaymentConfirmation;
import com.khangng.payment_service.kafka.PaymentProducer;
import com.khangng.payment_service.vnpay.VNPayRequest;
import com.khangng.payment_service.vnpay.VNPayService;
import com.khangng.payment_service.customer.CustomerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    private final VNPayService vnpayService;
    private final CustomerClient customerClient;
    public PaymentResponse createPayment(PaymentRequest paymentRequest, String ipAddress) throws UnsupportedEncodingException {
        String paymentUrl = "";
        var newPayment = Payment.builder()
                .paymentStatus(PaymentStatus.PENDING)
                .orderId(paymentRequest.orderId())
                .customerId(paymentRequest.customer().id())
                .paymentMethod(paymentRequest.paymentMethod())
                .amount(paymentRequest.amount())
                .build();
        if (paymentRequest.paymentMethod().equals(PaymentMethod.CASH)) {
            newPayment.setPaymentStatus(PaymentStatus.SUCCESS);
        } else if (paymentRequest.paymentMethod().equals(PaymentMethod.VNPAY)){
            VNPayRequest vnpayRequest = new VNPayRequest(
                newPayment.getOrderId().toString(),
                newPayment.getAmount(),
                ipAddress
            );
            paymentUrl = vnpayService.createPaymentUrl(vnpayRequest);
        }
        var savedPayment = paymentRepository.save(newPayment);
        
        if (paymentRequest.paymentMethod().equals(PaymentMethod.CASH)) {
            paymentProducer.sendPaymentConfirmation(
                new PaymentConfirmation(
                    savedPayment.getOrderId(),
                    savedPayment.getAmount(),
                    savedPayment.getPaymentMethod(),
                    paymentRequest.customer().firstName(),
                    paymentRequest.customer().lastName(),
                    paymentRequest.customer().email()
                )
            );
        }
        
        return new PaymentResponse(
            savedPayment.getId(),
            savedPayment.getAmount(),
            savedPayment.getOrderId(),
            savedPayment.getPaymentStatus(),
            savedPayment.getPaymentMethod(),
            paymentUrl
        );
    }
    
    public void handleVNPayCallback(Map<String, String> params) {
        System.out.println("VNPay callback received");
        if (params.get("vnp_ResponseCode").equals("00")) {
            var payment = paymentRepository.findByOrderId(UUID.fromString(params.get("vnp_TxnRef")));
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            CustomerResponse customer = customerClient.findCustomerById(payment.getCustomerId());
            paymentProducer.sendPaymentConfirmation(
                new PaymentConfirmation(
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    customer.firstName(),
                    customer.lastName(),
                    customer.email()
                )
            );
        }
//        vnp_Amount=10000000&vnp_BankCode=NCB&vnp_BankTranNo=VNP14559775&vnp_CardType=ATM&vnp_OrderInfo=Thanh+toan+don+hang%3A12345687&vnp_PayDate=20240819223653&vnp_ResponseCode=00&vnp_TmnCode=Q5M2CRIH&vnp_TransactionNo=14559775&vnp_TransactionStatus=00&vnp_TxnRef=12345687&vnp_SecureHash=1c4844f681d339545857f87e70e855a2dc0a56e9c9160e9d150dc37fbd94fe568b10cbf8d3b6c777726f071810cc86f2a5044cc447875faeb59b2e9a507a1e17
    }
}

