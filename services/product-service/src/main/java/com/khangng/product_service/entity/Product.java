package com.khangng.product_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {
    @Id
    private Long id;
    private String name;
    private String description;
    private Integer availableQuantity;
    private Double price;
    private String category;
}
