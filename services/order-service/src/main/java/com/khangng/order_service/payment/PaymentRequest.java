package com.khangng.order_service.payment;

import com.khangng.order_service.customer.CustomerRequest;
import com.khangng.order_service.order.PaymentMethod;

public record PaymentRequest(
    int orderId,
    double amount,
    PaymentMethod paymentMethod,
    String orderReference,
    CustomerRequest customer
) {
}