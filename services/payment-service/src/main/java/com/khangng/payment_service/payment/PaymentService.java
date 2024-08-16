package com.khangng.payment_service.payment;

import com.khangng.payment_service.entity.Payment;
import com.khangng.payment_service.kafka.PaymentConfirmation;
import com.khangng.payment_service.kafka.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    public int createPayment(PaymentRequest paymentRequest) {
        var newPayment = Payment.builder()
                .reference(paymentRequest.orderReference())
                .status(Status.PENDING)
                .orderId(paymentRequest.orderId())
                .paymentMethod(paymentRequest.paymentMethod())
                .amount(paymentRequest.amount())
                .build();
        var savedPayment = paymentRepository.save(newPayment);
        
        paymentProducer.sendPaymentConfirmation(
            new PaymentConfirmation(
                savedPayment.getOrderId(),
                savedPayment.getReference(),
                savedPayment.getAmount(),
                savedPayment.getPaymentMethod(),
                paymentRequest.customer().firstName(),
                paymentRequest.customer().lastName(),
                paymentRequest.customer().email()
            )
        );
        
        return savedPayment.getId();
    }
}
