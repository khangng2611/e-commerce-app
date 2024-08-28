package com.khangng.customer_service.customer;

import com.khangng.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerId(String customerId);
}
