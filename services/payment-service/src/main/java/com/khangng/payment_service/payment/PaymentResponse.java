package com.khangng.payment_service.payment;

import java.util.UUID;

public record PaymentResponse(
    int id,
    double amount,
    UUID orderId,
    PaymentStatus paymentStatus,
    PaymentMethod paymentMethod,
    String paymentUrl
) { }
