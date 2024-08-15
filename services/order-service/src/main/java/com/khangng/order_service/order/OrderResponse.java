package com.khangng.order_service.order;

import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.entity.Order;
import com.khangng.order_service.entity.OrderLine;
import com.khangng.order_service.order_line.OrderLineResponse;
import com.khangng.order_service.product.PurchaseResponse;
import java.util.List;

public record OrderResponse(
    int id,
    String reference,
    double totalAmount,
    PaymentMethod paymentMethod,
    String customerId,
    List<OrderLineResponse> orderLineResponseList
) {
    public OrderResponse (Order order, List<OrderLineResponse> orderLineResponseList) {
        this(
            order.getId(),
            order.getReference(),
            order.getTotalAmount(),
            order.getPaymentMethod(),
            order.getCustomerId(),
            orderLineResponseList
        );
    }
}
