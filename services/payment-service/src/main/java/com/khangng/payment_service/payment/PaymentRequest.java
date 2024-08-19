package com.khangng.payment_service.payment;

import com.khangng.payment_service.customer.CustomerRequest;

import java.util.UUID;

public record PaymentRequest(
    int id,
    double amount,
    PaymentMethod paymentMethod,
    UUID orderId,
    CustomerRequest customer
) {
}
