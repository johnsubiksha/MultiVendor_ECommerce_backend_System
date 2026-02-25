package com.example.ECommerce.controller;

import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.UserRepository;
import com.example.ECommerce.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder encoder;
    private final UserRepository repo;
    private final JwtUtil jwtUtil;

    public AuthController(
            PasswordEncoder encoder,
            UserRepository repo,
            JwtUtil jwtUtil
    ) {
        this.encoder = encoder;
        this.repo = repo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Enter your Email");
        }
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "Signup successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User dbUser = repo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(user.getPassword(), dbUser.getPassword())) {
            return jwtUtil.generateToken(dbUser.getEmail());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
