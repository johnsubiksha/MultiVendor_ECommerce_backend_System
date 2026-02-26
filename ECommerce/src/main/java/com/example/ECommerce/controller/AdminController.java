package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Seller;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.SellerRepository;
import com.example.ECommerce.repositories.UserRepository;
import com.example.ECommerce.Model.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    public AdminController(SellerRepository sellerRepository,
                           UserRepository userRepository) {
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/approve-seller/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveSeller(@PathVariable String userId) {

        // 1️⃣ approve seller request
        Seller seller = sellerRepository.findByUserId(userId);
        seller.setApproved(true);
        sellerRepository.save(seller);

        // 2️⃣ change user role
        User user = userRepository.findById(userId).get();
        user.setRole(Role.SELLER);
        userRepository.save(user);

        return "Seller approved successfully";
    }

    @GetMapping("/seller-requests")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Seller> getPendingSellerRequest() {
        return sellerRepository.findByApprovedFalse();
    }
}