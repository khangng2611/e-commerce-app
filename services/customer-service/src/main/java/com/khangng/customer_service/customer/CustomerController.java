package com.khangng.customer_service.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestHeader(value = "Authorization") String bearerToken) {
        return ResponseEntity.ok(customerService.create(bearerToken));
    }
    
    @PutMapping("/{customer-id}")
    public ResponseEntity<Void> updateByCustomerId(
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        customerService.updateByCustomerId(customerRequest);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }
    
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok().body(customerService.getByCustomerId(customerId));
    }
    
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteByCustomerId(@PathVariable("customer-id") String customerId) {
        customerService.deleteByCustomerId(customerId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/me")
    public ResponseEntity<Void> updateSelf(
            @RequestHeader(value = "Authorization") String bearerToken,
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        customerService.updateSelf(bearerToken, customerRequest);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping("/me")
    public ResponseEntity<CustomerResponse> getSelf(@RequestHeader(value = "Authorization") String bearerToken) {
        return ResponseEntity.ok().body(customerService.getSelf(bearerToken));
    }
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteSelf(@RequestHeader(value = "Authorization") String bearerToken) {
        customerService.deleteSelf(bearerToken);
        return ResponseEntity.noContent().build();
    }
}
