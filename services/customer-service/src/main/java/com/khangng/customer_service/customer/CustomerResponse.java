package com.khangng.customer_service.customer;

import com.khangng.customer_service.entity.Customer;

public record CustomerResponse (
    int id,
    String firstName,
    String lastName,
    String email
//    Address address
) {
    public CustomerResponse(Customer customer) {
        this(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
//                customer.getAddress()
       );
    }
}
