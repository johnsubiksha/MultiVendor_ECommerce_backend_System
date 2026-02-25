package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String Email);
    boolean existsByEmail(String email);
}
