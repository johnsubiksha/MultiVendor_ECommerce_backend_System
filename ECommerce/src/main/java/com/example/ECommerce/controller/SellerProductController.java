package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/products")
public class SellerProductController {

    @Autowired
    private ProductService productservice;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public Product addProduct(
            @RequestBody Product product,
            Authentication authentication) {

        String sellerId = authentication.getName();
        return productservice.addProduct(product, sellerId);
    }

    // 📄 VIEW OWN PRODUCTS
    @GetMapping
    @PreAuthorize("hasRole('SELLER')")
    public List<Product> getMyProducts(Authentication authentication) {

        String sellerId = authentication.getName();
        return productservice.getSellerProducts(sellerId);
    }

    // ✏️ UPDATE PRODUCT
    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public Product updateProduct(
            @PathVariable String productId,
            @RequestBody Product product,
            Authentication authentication) {

        String sellerId = authentication.getName();
        return productservice.updateProduct(productId, product, sellerId);
    }

    // ❌ DELETE PRODUCT
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public String deleteProduct(
            @PathVariable String productId,
            Authentication authentication) {

        String sellerId = authentication.getName();
        productservice.deleteProduct(productId, sellerId);
        return "Product deleted successfully";
    }
}
