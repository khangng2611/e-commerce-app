package com.khangng.customer_service.customer;

import com.khangng.customer_service.entity.Customer;
import com.khangng.customer_service.exception.CustomerNotFoundException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final JwtDecoder jwtDecoder;
    
    public CustomerResponse createCustomer(String bearerToken, CustomerRequest customerRequest) {
        Jwt jwt = jwtDecoder.decode(bearerToken.replace("Bearer ", ""));
        String userId = jwt.getClaimAsString("sub");
        String userEmail = jwt.getClaimAsString("email");
        String username = jwt.getClaimAsString("preferred_username");
        String firstName = jwt.getClaimAsString("given_name");
        String lastName = jwt.getClaimAsString("family_name");
        System.out.println(userId + " " + userEmail + " " + username + " " + firstName + " " + lastName);
        
        Customer customer = Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(customerRequest.address())
                .build();
        return new CustomerResponse(customerRepository.save(customer));
    }
    
    public void updateCustomer(CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(customerRequest.id()).orElseThrow(() ->
            new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", customerRequest.id()))
        );
        if (StringUtils.isNotBlank(customerRequest.firstName())) {
            existingCustomer.setFirstName(customerRequest.firstName());
        }
        if (StringUtils.isNotBlank(customerRequest.lastName())) {
            existingCustomer.setLastName(customerRequest.lastName());
        }
        if (StringUtils.isNotBlank(customerRequest.email())) {
            existingCustomer.setEmail(customerRequest.email());
        }
        if (customerRequest.address() != null) {
            existingCustomer.setAddress(customerRequest.address());
        }
        this.customerRepository.save(existingCustomer);
    }
    
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(CustomerResponse::new)
            .collect(Collectors.toList());
    }
    
    public CustomerResponse getCustomerById(String customerId) {
        return customerRepository.findById(customerId)
            .map(CustomerResponse::new)
            .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", customerId)));
    }
    
    public void deleteCustomer(String customerId) {
        customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", customerId)));
        customerRepository.deleteById(customerId);
    }
}
