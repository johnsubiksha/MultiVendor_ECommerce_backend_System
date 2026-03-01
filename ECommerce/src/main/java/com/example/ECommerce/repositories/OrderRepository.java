package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findBycustomerId(String customerId);
    List<Order> findBysellerId(String sellerId);
}
