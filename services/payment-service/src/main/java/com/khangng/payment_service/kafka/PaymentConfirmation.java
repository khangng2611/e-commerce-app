package com.khangng.payment_service.kafka;

import com.khangng.payment_service.payment.PaymentMethod;

import java.util.UUID;

public record PaymentConfirmation (
    UUID orderId,
    double amount,
    PaymentMethod paymentMethod,
    String customerFirstname,
    String customerLastname,
    String customerEmail
) {
}
