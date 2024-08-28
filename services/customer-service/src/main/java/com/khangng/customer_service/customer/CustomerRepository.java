package com.khangng.customer_service.customer;

import com.khangng.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
