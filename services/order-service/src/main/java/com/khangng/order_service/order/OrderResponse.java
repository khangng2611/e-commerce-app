package com.khangng.order_service.order;

import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.entity.Order;
import com.khangng.order_service.entity.OrderLine;
import com.khangng.order_service.order_line.OrderLineResponse;
import com.khangng.order_service.product.PurchaseResponse;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
    UUID id,
    double totalAmount,
    PaymentMethod paymentMethod,
    String customerId,
    List<OrderLineResponse> orderLineResponseList,
    String paymentUrl
) {
    public OrderResponse (Order order, List<OrderLineResponse> orderLineResponseList, String paymentUrl) {
        this(
            order.getId(),
            order.getTotalAmount(),
            order.getPaymentMethod(),
            order.getCustomerId(),
            orderLineResponseList,
            paymentUrl
        );
    }
}
