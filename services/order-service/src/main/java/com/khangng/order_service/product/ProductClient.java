package com.khangng.order_service.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;

@FeignClient(
    name = "product-service",
    url = "${application.config.product-url}"
)
public interface ProductClient {
    
    @PostMapping("/purchase")
    Optional<List<PurchaseResponse>> purchase(
        @RequestHeader("Authorization") String bearerToken,
        @RequestBody List<PurchaseRequest> products
    );
}
