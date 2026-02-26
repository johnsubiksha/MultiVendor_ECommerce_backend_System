package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Seller;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SellerRepository extends MongoRepository<Seller, String> {
    Seller findByUserId(String userId);
    List<Seller> findByApprovedFalse();
}
