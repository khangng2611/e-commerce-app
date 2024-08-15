package com.khangng.order_service.order;

import com.khangng.order_service.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record OrderRequest(
    int id,
    String reference,
    @Positive(message = "Total amount should be positive")
    double totalAmount,
    @NotNull(message = "Payment method is mandatory")
    PaymentMethod paymentMethod,
    @NotNull(message = "Customer id is mandatory")
    @NotEmpty(message = "Customer id should not be empty")
    @NotBlank(message = "Customer id should not be blank")
    String customerId,
    @NotEmpty(message = "Products should not be empty")
    List<PurchaseRequest> products
) { }
