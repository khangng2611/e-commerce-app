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
    public ResponseEntity<CustomerResponse> createCustomer(
            @RequestHeader(value = "Authorization") String bearerToken,
            @RequestBody @Valid CustomerRequest customerRequest
    ) {
        System.out.println(bearerToken);
        return ResponseEntity.ok(customerService.createCustomer(bearerToken, customerRequest));
    }
    
    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
    
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok().body(customerService.getCustomerById(customerId));
    }
    
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
