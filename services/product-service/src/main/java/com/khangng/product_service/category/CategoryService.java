package com.khangng.product_service.category;

import com.khangng.product_service.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .name(categoryRequest.name())
                .description(categoryRequest.description())
                .build();
        return categoryRepository.save(newCategory);
    }
}
