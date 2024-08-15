package com.khangng.order_service.product;

public record PurchaseResponse(
        int productId,
        String name,
        String description,
        int quantity,
        double price
) {}
