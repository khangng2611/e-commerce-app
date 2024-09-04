package com.khangng.notification_service.kafka.order;

public record CustomerResponse(
    String customerId,
    String email,
    String firstName,
    String lastName
) {
}