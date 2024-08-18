package com.khangng.payment_service.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest));
    }
}
