package com.khangng.order_service.order;

import com.khangng.order_service.product.PurchaseRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.UUID;

public record OrderRequest(
    UUID id,
    @Positive(message = "Total amount should be positive")
    double totalAmount,
    @NotNull(message = "Payment method is mandatory")
    PaymentMethod paymentMethod,
    @NotEmpty(message = "Products should not be empty")
    List<PurchaseRequest> products
) { }
