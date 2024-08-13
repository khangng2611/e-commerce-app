package com.khangng.product_service.product;

import com.khangng.product_service.entity.Category;
import com.khangng.product_service.entity.Product;

public record ProductResponse (
    int id,
    String name,
    String description,
    double price,
    int availableQuantity,
    int categoryId
) {
    public ProductResponse (Product product) {
        this(product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAvailableQuantity(),
            product.getCategory().getId()
        );
    }
}
