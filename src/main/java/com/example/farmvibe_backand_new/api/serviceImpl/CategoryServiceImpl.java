package com.example.farmvibe_backand_new.api.serviceImpl;

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
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + id));
    }

    // ---------------- READ ALL ----------------
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // ---------------- UPDATE ----------------
    @Override
    public Category updateCategory(Long id, Category category) {

        Category existing = getCategoryById(id);

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
        Category existing = getCategoryById(id);
        categoryRepository.delete(existing);
    }


}
