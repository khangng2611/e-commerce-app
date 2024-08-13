package com.khangng.product_service.product;

public record PurchaseRequest(
    String customerId,
    String productId,
    int quantity
) {}
