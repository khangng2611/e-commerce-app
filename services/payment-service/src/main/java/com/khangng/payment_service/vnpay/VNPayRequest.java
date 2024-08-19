package com.khangng.payment_service.vnpay;

public record VNPayRequest(
        String orderId,
        double amount,
        String ipAddress
) {
}
