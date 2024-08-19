package com.khangng.order_service.payment;

import com.khangng.order_service.order.PaymentMethod;

import java.util.UUID;

public record PaymentResponse(
        int id,
        double amount,
        UUID orderId,
        PaymentStatus status,
        PaymentMethod paymentMethod,
        String paymentUrl
) { }
