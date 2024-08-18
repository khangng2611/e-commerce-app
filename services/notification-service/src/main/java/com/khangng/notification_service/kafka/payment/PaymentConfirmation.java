package com.khangng.notification_service.kafka.payment;

public record PaymentConfirmation(
    int orderId,
    String orderReference,
    double amount,
    PaymentMethod paymentMethod,
    String customerFirstname,
    String customerLastname,
    String customerEmail
) {
}
