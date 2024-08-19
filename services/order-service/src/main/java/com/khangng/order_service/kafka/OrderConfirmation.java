package com.khangng.order_service.kafka;

import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.order.PaymentMethod;
import com.khangng.order_service.product.PurchaseResponse;

import java.util.List;
import java.util.UUID;

public record OrderConfirmation (
        UUID orderId,
        double totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
