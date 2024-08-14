package com.khangng.product_service.handler;

import com.khangng.product_service.exception.NotFoundException;
import com.khangng.product_service.exception.PurchaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({NotFoundException.class, PurchaseException.class})
    public ResponseEntity<ErrorResponse<String>> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse<>(e.getMessage()));
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String,String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String,String> errors = new HashMap<>();
        e.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return ResponseEntity.badRequest().body(new ErrorResponse<>(errors));
    }
}
