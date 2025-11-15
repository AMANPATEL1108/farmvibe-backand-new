package com.example.farmvibe_backand_new.api.repository;

import com.example.farmvibe_backand_new.api.model.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Long> {
}