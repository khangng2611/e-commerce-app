package com.khangng.payment_service.handler;

public record ErrorResponse<T>(T errors) {
}
