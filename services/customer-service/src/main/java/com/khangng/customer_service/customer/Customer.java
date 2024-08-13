package com.khangng.customer_service.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(collection = "customers")
public class Customer {
    @Id()
    private String id;
    private String fistName;
    private String lastName;
    private String email;
    private Address address;
    
}
