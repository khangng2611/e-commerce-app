package com.khangng.payment_service.payment;

import com.khangng.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByOrderId(UUID orderId);
}
