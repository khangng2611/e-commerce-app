package com.khangng.customer_service.customer;

import com.khangng.customer_service.entity.Customer;

public record CustomerResponse (
    int id,
    String customerId,
    String username,
    String email,
    String firstName,
    String lastName,
    String address,
    String phoneNumber
    
) {
    public CustomerResponse(Customer customer) {
        this(
                customer.getId(),
                customer.getCustomerId(),
                customer.getUsername(),
                customer.getEmail(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getPhoneNumber()
       );
    }
}
