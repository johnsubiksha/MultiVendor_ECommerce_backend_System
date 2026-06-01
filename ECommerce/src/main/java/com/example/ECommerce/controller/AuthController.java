package com.example.ECommerce.controller;

import com.example.ECommerce.DTO.ForgotPasswordRequest;
import com.example.ECommerce.DTO.ResetPasswordRequest;
import com.example.ECommerce.DTO.VerifyOtpRequest;
import com.example.ECommerce.Enum.Role;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.UserRepository;
import com.example.ECommerce.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
        user.setRole(Role.CUSTOMER);
        repo.save(user);
        return "Signup successful";
    }

    @PostMapping("/signin")
    public String login(@RequestBody User user) {
        User dbUser = repo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(user.getPassword(), dbUser.getPassword())) {
            return jwtUtil.generateToken(dbUser.getId(),dbUser.getRole().name());
        }

        throw new RuntimeException("Invalid credentials");
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf(
                (int)(100000 + Math.random() * 900000));

        user.setOtp(otp);

        user.setOtpExpiryTime(
                LocalDateTime.now().plusMinutes(5));

        repo.save(user);

        return "OTP Generated: " + otp;
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("OTP Expired");
        }

        return "OTP Verified Successfully";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody ResetPasswordRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (user.getOtpExpiryTime()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("OTP Expired");
        }

        user.setPassword(
                encoder.encode(request.getNewPassword()));

        user.setOtp(null);
        user.setOtpExpiryTime(null);

        repo.save(user);

        return "Password Reset Successful";
    }


}
