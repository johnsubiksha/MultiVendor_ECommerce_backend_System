package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findBySellerId(String sellerid);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory(String category, Pageable pageable);
    Integer getStock(String productId);
    Optional<Product> findById(String productId);
}
