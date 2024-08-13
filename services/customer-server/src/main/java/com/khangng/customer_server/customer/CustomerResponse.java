package com.khangng.customer_server.customer;

public record CustomerResponse (
    String id,
//    String firstName,
//    String lastName,
    String email
//    Address address
) {
    public CustomerResponse(Customer customer) {
        this(
                customer.getId(),
//                customer.getFirstName(),
//                customer.getLastName(),
                customer.getEmail()
//                customer.getAddress()
       );
    }
}
