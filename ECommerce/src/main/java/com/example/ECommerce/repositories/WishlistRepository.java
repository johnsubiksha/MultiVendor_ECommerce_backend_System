package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<Wishlist, String> {

    Optional<Wishlist> findByCustomerId(String customerId);

}