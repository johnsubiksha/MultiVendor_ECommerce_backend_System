package com.example.ECommerce.controller;

import com.example.ECommerce.DTO.ChangePasswordRequest;
import com.example.ECommerce.DTO.UpdateProfileRequest;
import com.example.ECommerce.DTO.UserProfileResponse;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserController(UserRepository userRepository,
                          PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // GET PROFILE
    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getProfile(
            Authentication authentication) {

        User user = userRepository.findById(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        UserProfileResponse response =
                new UserProfileResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setAge(user.getAge());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        return response;
    }

    // UPDATE PROFILE
    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public User updateProfile(
            @RequestBody UpdateProfileRequest request,
            Authentication authentication) {

        User user = userRepository.findById(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setPhone(request.getPhone());

        return userRepository.save(user);
    }

    // CHANGE PASSWORD
    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {

        User user = userRepository.findById(
                        authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!encoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Old password is incorrect");
        }

        user.setPassword(
                encoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully";
    }
}
