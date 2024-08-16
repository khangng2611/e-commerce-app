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
import com.khangng.order_service.product.ProductClient;
import com.khangng.order_service.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Get customer
        CustomerResponse customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new OrderException("CustomerId :: Customer not found"));
        
        // Purchase product
        List<PurchaseResponse> purchaseResponseList = productClient.purchase(orderRequest.products())
                .orElseThrow(() -> new OrderException("Purchase Product :: Invalid products"));
        
        // save order
        Order newOrder = Order.builder()
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.totalAmount())
                .customerId(orderRequest.customerId())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
        var savedNewOrder = orderRepository.save(newOrder);
        
        // save order line
        for (var purchaseRequest : orderRequest.products()) {
            orderLineService.createOrderLine(
                savedNewOrder.getId(),
                purchaseRequest.productId(),
                purchaseRequest.quantity()
            );
        }
        
        // todo start payment
        paymentClient.createPayment(
            new PaymentRequest(
                savedNewOrder.getId(),
                savedNewOrder.getTotalAmount(),
                savedNewOrder.getPaymentMethod(),
                savedNewOrder.getReference(),
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
                savedNewOrder.getReference(),
                savedNewOrder.getTotalAmount(),
                savedNewOrder.getPaymentMethod(),
                customer,
                purchaseResponseList
            )
        );
        
        // if payment success, send payment notification
        
        return null;
    }
    
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(order -> new OrderResponse(order, orderLineService.findOrderLineByOrderId(order.getId())))
            .toList();
    }
    
    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
            .map(order -> new OrderResponse(order, orderLineService.findOrderLineByOrderId(orderId)))
            .orElseThrow(() -> new OrderException("Order not found"));
    }
}
