package com.khangng.payment_service.payment;

public record PaymentResponse(
    int id,
    String reference,
    double amount,
    int orderId,
    Status status,
    PaymentMethod paymentMethod,
    String paymentUrl
) { }
