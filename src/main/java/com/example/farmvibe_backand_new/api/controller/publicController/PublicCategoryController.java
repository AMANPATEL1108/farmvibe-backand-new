package com.example.farmvibe_backand_new.api.controller.publicController;

import com.example.farmvibe_backand_new.api.dto.CategoryDTO;
import com.example.farmvibe_backand_new.api.model.Category;
import com.example.farmvibe_backand_new.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/categories")
@RequiredArgsConstructor
public class PublicCategoryController {


    @Autowired
    private  CategoryService categoryService;

    // ---------------- READ ALL ----------------
    @GetMapping("/get-all-categories")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // ---------------- READ BY ID ----------------

    @GetMapping("get-category-by-id/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("get-product-category/{id}")
    public CategoryDTO getProductCategoryById(@PathVariable Long id) {
        return categoryService.getProductCategoryById(id);
    }

}
