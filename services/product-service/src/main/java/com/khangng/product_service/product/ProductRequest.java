package com.khangng.product_service.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
    @NotNull(message = "Name is mandatory")
    String name,
    String description,
    @Positive(message = "Price should be positive")
    double price,
    @Positive(message = "Available quantity should be positive")
    int availableQuantity,
    @NotNull(message = "Category id is mandatory")
    int categoryId
) {}
