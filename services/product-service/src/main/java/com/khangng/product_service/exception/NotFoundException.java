package com.khangng.product_service.exception;

import lombok.Data;

@Data
public class NotFoundException extends RuntimeException {
    private final String message;
}
