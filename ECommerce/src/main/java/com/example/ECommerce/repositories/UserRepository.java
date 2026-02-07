package com.example.ECommerce.repositories;

import com.example.ECommerce.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
