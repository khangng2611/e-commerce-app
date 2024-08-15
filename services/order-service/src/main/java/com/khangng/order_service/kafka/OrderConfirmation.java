package com.khangng.order_service.kafka;

import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.order.PaymentMethod;
import com.khangng.order_service.product.PurchaseResponse;

import java.util.List;

public record OrderConfirmation (
        String orderReference,
        double totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}
