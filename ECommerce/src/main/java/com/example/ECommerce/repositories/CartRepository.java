package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart,String> {
    Optional<Cart> findBycustomerId(String customerId);
}
