package com.khangng.order_service.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "payment-service",
    url = "${application.config.payment-url}"
)
public interface PaymentClient {
    @PostMapping
    PaymentResponse createPayment(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody PaymentRequest paymentRequest
    );
}
