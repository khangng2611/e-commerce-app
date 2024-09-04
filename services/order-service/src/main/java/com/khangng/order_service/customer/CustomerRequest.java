package com.khangng.order_service.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Validated
public record CustomerRequest(
        @NotNull(message = "Customer id is mandatory")
        @NotEmpty(message = "Customer id should not be empty")
        @NotBlank(message = "Customer id should not be blank")
        String customerId,
        @NotNull(message = "First name is mandatory")
        String firstName,
        @NotNull(message = "Last name is mandatory")
        String lastName,
        @NotNull(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email
) {
}
