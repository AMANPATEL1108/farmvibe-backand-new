package com.example.farmvibe_backand_new.api.serviceImpl;

import com.example.farmvibe_backand_new.api.dto.CategoryDTO;
import com.example.farmvibe_backand_new.api.dto.ProductDetailsDTO;
import com.example.farmvibe_backand_new.api.service.CategoryService;


import com.example.farmvibe_backand_new.api.model.Category;
import com.example.farmvibe_backand_new.api.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ---------------- CREATE ----------------
    @Override
    public Category createCategory(Category category) {

        // Validation to avoid null or empty values
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }

        if (category.getDescription() == null || category.getDescription().isBlank()) {
            throw new IllegalArgumentException("Category description cannot be null or empty");
        }

        if (category.getCategory_image_url() == null || category.getCategory_image_url().isBlank()) {
            throw new IllegalArgumentException("Category image URL cannot be null or empty");
        }

        return categoryRepository.save(category);
    }

    // ---------------- READ BY ID ----------------
//    @Override
//    public Category getCategoryById(Long id) {
//        return categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
//    }

    @Override
    public CategoryDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));

        // Convert products to ProductDetailsDTO
        List<ProductDetailsDTO> productDTOs = category.getProducts()
                .stream()
                .map(product -> new ProductDetailsDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImageUrl(),
                        product.getPrice(),
                        product.getWeight(),
                        product.getStock(),
                        product.getBenefits()
                ))
                .toList();

        // Return the CategoryDTO
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCategory_image_url(),
                productDTOs
        );
    }


    // ---------------- READ ALL ----------------
    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        // Map each Category entity to CategoryDTO
        return categories.stream()
                .map(category -> new CategoryDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription(),
                        category.getCategory_image_url(),
                        null // or empty list if you don't want to include products here
                ))
                .toList();
    }


    // ---------------- UPDATE ----------------
    @Override
    public Category updateCategory(Long id, Category category) {

        Category existing = categoryRepository.getCategoryById(id);

        // Update only non-null values
        if (category.getName() != null && !category.getName().isBlank()) {
            existing.setName(category.getName());
        }

        if (category.getDescription() != null && !category.getDescription().isBlank()) {
            existing.setDescription(category.getDescription());
        }

        if (category.getCategory_image_url() != null && !category.getCategory_image_url().isBlank()) {
            existing.setCategory_image_url(category.getCategory_image_url());
        }

        return categoryRepository.save(existing);
    }

    // ---------------- DELETE ----------------
    @Override
    public void deleteCategory(Long id) {
        Category existing = categoryRepository.getCategoryById(id);
        categoryRepository.delete(existing);
    }


}
