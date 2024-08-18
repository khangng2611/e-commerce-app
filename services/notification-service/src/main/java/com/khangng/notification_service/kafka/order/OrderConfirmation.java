package com.khangng.notification_service.kafka.order;

import com.khangng.notification_service.kafka.payment.PaymentMethod;

import java.util.List;

public record OrderConfirmation (
    int orderId,
    String orderReference,
    double totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products
) {
}
