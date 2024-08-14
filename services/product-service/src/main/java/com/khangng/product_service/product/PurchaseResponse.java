package com.khangng.product_service.product;

import com.khangng.product_service.entity.Product;

public record PurchaseResponse(
    int productId,
    String name,
    String description,
    int quantity,
    double price
) {
    public PurchaseResponse(Product product, int quantity) {
        this(product.getId(),
            product.getName(),
            product.getDescription(),
            quantity,
            product.getPrice()
        );
    }
}
