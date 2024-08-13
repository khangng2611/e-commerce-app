package com.khangng.customer_service.handler;

import java.util.Map;

public record ErrorResponse<T>(T errors) {}
