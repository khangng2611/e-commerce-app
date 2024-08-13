package com.khangng.product_service.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
    @NotNull(message = "Name is mandatory")
    String name,
    String description
    
) {}
