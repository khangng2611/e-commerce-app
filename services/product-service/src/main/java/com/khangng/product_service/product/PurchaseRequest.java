package com.khangng.product_service.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
    @NotNull(message = "Product id is mandatory")
    int productId,
    @Positive(message = "Quantity should be positive")
    int quantity
) {}
