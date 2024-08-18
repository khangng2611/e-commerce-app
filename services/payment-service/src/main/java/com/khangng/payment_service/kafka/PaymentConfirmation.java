package com.khangng.payment_service.kafka;

import com.khangng.payment_service.payment.PaymentMethod;

public record PaymentConfirmation (
    int orderId,
    String orderReference,
    double amount,
    PaymentMethod paymentMethod,
    String customerFirstname,
    String customerLastname,
    String customerEmail
) {
}
