package com.example.farmvibe_backand_new.api.repository;

import com.example.farmvibe_backand_new.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category getCategoryById(Long id);
}
