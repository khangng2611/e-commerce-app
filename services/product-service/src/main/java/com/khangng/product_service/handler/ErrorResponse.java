package com.khangng.product_service.handler;

public record ErrorResponse<T>(T errors) {
}
