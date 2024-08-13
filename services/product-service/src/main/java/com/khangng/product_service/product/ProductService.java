package com.khangng.product_service.product;

import com.khangng.product_service.category.CategoryRepository;
import com.khangng.product_service.entity.Category;
import com.khangng.product_service.entity.Product;
import com.khangng.product_service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository
                .findById(productRequest.categoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));
        
        Product newProduct = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .availableQuantity(productRequest.availableQuantity())
                .category(category)
                .build();
        return new ProductResponse(productRepository.save(newProduct));
    }
    
    public ProductResponse getProductById(String productId) {
        Product product = productRepository
                .findById(Integer.parseInt(productId))
                .orElseThrow(() -> new NotFoundException("Product not found"));
        return new ProductResponse(product);
    }
    
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
    
    public void purchaseProduct(PurchaseRequest purchaseRequest) {
    }
}
