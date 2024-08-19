package com.khangng.notification_service.kafka.order;

import com.khangng.notification_service.kafka.payment.PaymentMethod;

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
