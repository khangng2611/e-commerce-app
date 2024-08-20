package com.khangng.order_service.customer;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@FeignClient(
    name = "customer-service",
    url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/{customer-id}")
    Optional<CustomerResponse> findCustomerById(
        @RequestHeader("Authorization") String bearerToken,
        @PathVariable("customer-id") String customerId
    ) throws FeignException;
}
