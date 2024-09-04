package com.khangng.order_service.customer;

public record CustomerResponse(
    String customerId,
    String email,
    String firstName,
    String lastName
) {
}
