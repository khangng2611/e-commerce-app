package com.khangng.product_service.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        return ResponseEntity.ok().body(productService.createProduct(productRequest));
    }
    
    @PostMapping("/purchase")
    public ResponseEntity<Void> purchaseProduct(@RequestBody @Valid PurchaseRequest purchaseRequest) {
        productService.purchaseProduct(purchaseRequest);
        return ResponseEntity.accepted().build();
    }
    
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("product-id") String productId) {
        return ResponseEntity.ok().body(productService.getProductById(productId));
    }
    
    
    
    

}
