package com.khangng.customer_server.handler;

import java.util.Map;

public record ErrorResponse<T>(T errors) {}
