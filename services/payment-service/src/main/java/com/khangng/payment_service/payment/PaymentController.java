package com.khangng.payment_service.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest paymentRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ResponseEntity.ok(paymentService.createPayment(paymentRequest, ipAddress));
    }
    
    @GetMapping("/vnpay/callback")
    public ResponseEntity<Void> vnPayCallback(@RequestParam Map<String, String> params) {
        paymentService.handleVNPayCallback(params);
        return ResponseEntity.ok().build();
    }
}
