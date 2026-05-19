package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.Address;
import com.example.ECommerce.Model.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends MongoRepository<Address,String> {

    List<Address> findByCustomerId(String customerId);
}
