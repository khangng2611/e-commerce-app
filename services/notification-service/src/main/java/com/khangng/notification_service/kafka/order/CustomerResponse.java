package com.khangng.notification_service.kafka.order;

public record CustomerResponse(
    String id,
    String firstName,
    String lastName,
    String email
) {
}