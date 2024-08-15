package com.khangng.order_service.order_line;

public record OrderLineResponse(
    int orderId,
    int productId,
    int quantity
) { }
