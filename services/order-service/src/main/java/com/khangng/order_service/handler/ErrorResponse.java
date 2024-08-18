package com.khangng.order_service.handler;

public record ErrorResponse<T>(T errors) {
}
