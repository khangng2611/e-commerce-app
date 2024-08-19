package com.khangng.payment_service.vnpay;

public record VNPayRequest(
        String orderReference,
        double amount,
        String ipAddress
) {
}
