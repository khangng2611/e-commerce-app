package com.khangng.order_service.customer;

public record CustomerResponse(
    String id,
    String firstName,
    String lastName,
    String email
) {
}
