package com.khangng.product_service.exception;

import lombok.Data;

@Data
public class PurchaseException extends RuntimeException {
    private final String message;
}
