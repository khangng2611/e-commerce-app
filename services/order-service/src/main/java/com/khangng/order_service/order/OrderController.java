package com.khangng.order_service.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder (
        @RequestHeader(value = "Authorization") String bearerToken,
        @RequestBody @Valid OrderRequest orderRequest
    ) {
        return ResponseEntity.ok().body(orderService.createOrder(bearerToken, orderRequest));
    }
    
    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }
    
    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> findById(
            @PathVariable("order-id") String orderId
    ) {
        return ResponseEntity.ok(orderService.findById(UUID.fromString(orderId)));
    }
    
    @GetMapping("/me")
    public ResponseEntity<OrderResponse> findAllSelf(
            @RequestHeader(value = "Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(orderService.findAllSelf(bearerToken));
    }
    
    @GetMapping("/me/{order-id}")
    public ResponseEntity<OrderResponse> findSelfById(
            @RequestHeader(value = "Authorization") String bearerToken,
            @PathVariable("order-id") String orderId
    ) {
        return ResponseEntity.ok(orderService.findSelfById(UUID.fromString(orderId)));
    }
}
