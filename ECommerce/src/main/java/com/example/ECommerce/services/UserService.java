package com.example.ECommerce.services;

import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
