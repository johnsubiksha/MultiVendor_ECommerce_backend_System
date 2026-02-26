package com.example.ECommerce.controller;

import com.example.ECommerce.services.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> applyForSeller(Authentication authentication) {

        String userId = authentication.getName(); // JWT subject = userId
        sellerService.applyForSeller(userId);

        return ResponseEntity.ok("Seller apply request submitted");
    }
}