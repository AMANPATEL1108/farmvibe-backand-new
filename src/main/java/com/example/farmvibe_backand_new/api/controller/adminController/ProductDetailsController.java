package com.example.farmvibe_backand_new.api.controller.adminController;


import com.example.farmvibe_backand_new.api.dto.ProductDetailsDTO;
import com.example.farmvibe_backand_new.api.model.ProductDetails;
import com.example.farmvibe_backand_new.api.service.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductDetailsController {

    private final ProductDetailsService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductDetails>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/create-product")
    public ResponseEntity<ProductDetails> createProduct(@RequestBody ProductDetails product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<ProductDetails> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDetails product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/delete-product-by-id/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product Deleted Successfully");
    }
}