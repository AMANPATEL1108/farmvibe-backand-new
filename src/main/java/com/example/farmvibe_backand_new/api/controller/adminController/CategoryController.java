package com.example.farmvibe_backand_new.api.controller.adminController;

import com.example.farmvibe_backand_new.api.dto.CategoryDTO;
import com.example.farmvibe_backand_new.api.model.Category;
import com.example.farmvibe_backand_new.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ---------------- CREATE ----------------
    @PostMapping("/create-category")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    // ---------------- READ ALL ----------------
    @GetMapping("/get-all-categories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ---------------- READ BY ID ----------------

    @GetMapping("get-category-by-id/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("update-category-by-id/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("delete-category-by-id/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
}
