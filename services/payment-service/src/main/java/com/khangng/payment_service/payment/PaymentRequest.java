package com.khangng.payment_service.payment;

public record PaymentRequest(
    int id,
    double amount,
    PaymentMethod paymentMethod,
    int orderId,
    String orderReference,
    CustomerRequest customer
) {
}
