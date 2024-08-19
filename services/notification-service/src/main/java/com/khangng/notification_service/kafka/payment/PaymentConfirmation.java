package com.khangng.notification_service.kafka.payment;

import java.util.UUID;

public record PaymentConfirmation(
    UUID orderId,
    double amount,
    PaymentMethod paymentMethod,
    String customerFirstname,
    String customerLastname,
    String customerEmail
) {
}
