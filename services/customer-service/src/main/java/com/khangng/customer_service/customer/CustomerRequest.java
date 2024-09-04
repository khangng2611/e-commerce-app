package com.khangng.customer_service.customer;

import jakarta.validation.constraints.Email;

public record CustomerRequest (
        String customerId,
        String firstName,
        String lastName,
        @Email(message = "Email should be valid")
        String email,
        String address,
        String phoneNumber
) {}
