package com.khangng.product_service.product;

import com.khangng.product_service.category.CategoryRepository;
import com.khangng.product_service.entity.Category;
import com.khangng.product_service.entity.Product;
import com.khangng.product_service.exception.NotFoundException;
import com.khangng.product_service.exception.PurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
                .toList();
    }
    
    public List<PurchaseResponse> purchaseProduct(List<PurchaseRequest> purchaseRequest) {
        List<Integer> productIdList = purchaseRequest.stream().map(PurchaseRequest::productId).toList();
        List<Product> productList = productRepository.findAllById(productIdList);
        if (productList.size() != productIdList.size()) {
            throw new PurchaseException("There is one or more product not found");
        }
        
        List<PurchaseResponse> purchasedProducts = new ArrayList<>();
        List<PurchaseRequest> sortedPurchaseRequest = purchaseRequest.stream()
                .sorted(Comparator.comparing(PurchaseRequest::productId))
                .toList();
        for (int i = 0; i< sortedPurchaseRequest.size(); i++) {
            PurchaseRequest tempPurchaseRequest = sortedPurchaseRequest.get(i);
            Product tempProduct = productList.get(i);
            if (tempPurchaseRequest.quantity() > tempProduct.getAvailableQuantity()) {
                throw new PurchaseException("Not enough quantity for product with id " + tempProduct.getId());
            }
            tempProduct.setAvailableQuantity(tempProduct.getAvailableQuantity() - tempPurchaseRequest.quantity());
            productRepository.save(tempProduct);
            purchasedProducts.add(new PurchaseResponse(tempProduct, tempPurchaseRequest.quantity()));
        }
        return purchasedProducts;
    }
}
