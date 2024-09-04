package com.khangng.order_service.order;

import com.khangng.order_service.customer.CustomerResponse;
import com.khangng.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByCustomerId(String customerId);
}
