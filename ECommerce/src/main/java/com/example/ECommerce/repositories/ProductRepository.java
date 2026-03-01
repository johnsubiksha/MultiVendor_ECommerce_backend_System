package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findBySellerId(String sellerid);
}
