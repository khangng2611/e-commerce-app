package com.khangng.order_service.payment;

import com.khangng.order_service.customer.CustomerRequest;
import com.khangng.order_service.order.PaymentMethod;

import java.util.UUID;

public record PaymentRequest(
    UUID orderId,
    double amount,
    PaymentMethod paymentMethod,
    CustomerRequest customer
) {
}