package com.khangng.product_service.category;

import com.khangng.product_service.entity.Category;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body(categoryService.createCategory(categoryRequest));
    }
    
}
