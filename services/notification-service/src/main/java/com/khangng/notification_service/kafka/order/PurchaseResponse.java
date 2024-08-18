package com.khangng.notification_service.kafka.order;

public record PurchaseResponse(
    int productId,
    String name,
    String description,
    int quantity,
    double price
) {}