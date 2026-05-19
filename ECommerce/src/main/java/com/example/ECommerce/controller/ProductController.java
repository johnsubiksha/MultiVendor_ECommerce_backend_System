package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String keyword) {

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @GetMapping("/filter")
    public List<Product> filterByCategory(@RequestParam String category) {

        return productRepository.findByCategory(category);
    }
}
