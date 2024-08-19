package com.khangng.payment_service.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record CustomerRequest(
        String id,
        @NotNull(message = "First name is mandatory")
        String firstName,
        @NotNull(message = "Last name is mandatory")
        String lastName,
        @NotNull(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email
) {
}
