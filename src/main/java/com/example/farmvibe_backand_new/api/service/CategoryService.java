package com.example.farmvibe_backand_new.api.service;


import com.example.farmvibe_backand_new.api.model.Category;
import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    void deleteCategory(Long id);
}
