package com.khangng.order_service.order;

import com.khangng.order_service.customer.CustomerClient;
import com.khangng.order_service.customer.CustomerRequest;
import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.entity.Order;
import com.khangng.order_service.entity.OrderLine;
import com.khangng.order_service.exception.OrderException;
import com.khangng.order_service.kafka.OrderConfirmation;
import com.khangng.order_service.kafka.OrderProducer;
import com.khangng.order_service.order_line.OrderLineRepository;
import com.khangng.order_service.order_line.OrderLineResponse;
import com.khangng.order_service.order_line.OrderLineService;
import com.khangng.order_service.payment.PaymentClient;
import com.khangng.order_service.payment.PaymentRequest;
import com.khangng.order_service.payment.PaymentResponse;
import com.khangng.order_service.product.ProductClient;
import com.khangng.order_service.product.PurchaseResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Get customer
        CustomerResponse customer = null;
        try {
            customer = customerClient.findCustomerById(orderRequest.customerId())
                    .orElseThrow(() -> new OrderException("Customer :: Not found with id %s".formatted(orderRequest.customerId())));
        } catch (FeignException e) {
            throw new OrderException("Customer :: Not found with id %s".formatted(orderRequest.customerId()));
        }
        
        // Purchase product
        List<PurchaseResponse> purchaseResponseList = productClient.purchase(orderRequest.products())
                .orElseThrow(() -> new OrderException("Purchase Product :: Invalid products"));
        
        // save order
        Order newOrder = Order.builder()
                .totalAmount(orderRequest.totalAmount())
                .customerId(orderRequest.customerId())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
        var savedNewOrder = orderRepository.save(newOrder);
        
        // save order line
        List<OrderLineResponse> orderLineResponseList = new ArrayList<>();
        for (var purchaseRequest : orderRequest.products()) {
            OrderLineResponse orderLineResponse = orderLineService.createOrderLine(
                savedNewOrder.getId(),
                purchaseRequest.productId(),
                purchaseRequest.quantity()
            );
            orderLineResponseList.add(orderLineResponse);
        }
        
        // save payment
        PaymentResponse paymentResponse = paymentClient.createPayment(
            new PaymentRequest(
                savedNewOrder.getId(),
                savedNewOrder.getTotalAmount(),
                savedNewOrder.getPaymentMethod(),
                new CustomerRequest(
                    customer.id(),
                    customer.firstName(),
                    customer.lastName(),
                    customer.email()
                )
            )
        );
        
        // send order notification
        orderProducer.sendOrderConfirmation(
            new OrderConfirmation(
                savedNewOrder.getId(),
                savedNewOrder.getTotalAmount(),
                savedNewOrder.getPaymentMethod(),
                customer,
                purchaseResponseList
            )
        );
        
        // if payment success, send payment notification
        
        return new OrderResponse(
            savedNewOrder.getId(),
            savedNewOrder.getTotalAmount(),
            savedNewOrder.getPaymentMethod(),
            savedNewOrder.getCustomerId(),
            orderLineResponseList,
            paymentResponse.paymentUrl()
        );
    }
    
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(order -> new OrderResponse(order, orderLineService.findOrderLineByOrderId(order.getId()), null))
            .toList();
    }
    
    public OrderResponse findById(UUID orderId) {
        return orderRepository.findById(orderId)
            .map(order -> new OrderResponse(order, orderLineService.findOrderLineByOrderId(orderId), null))
            .orElseThrow(() -> new OrderException("Order not found"));
    }
}
