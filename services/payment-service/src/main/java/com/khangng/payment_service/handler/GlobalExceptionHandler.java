package com.khangng.payment_service.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ErrorResponse<String>> handleUnsupportedEncodingException(UnsupportedEncodingException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
    }
}
