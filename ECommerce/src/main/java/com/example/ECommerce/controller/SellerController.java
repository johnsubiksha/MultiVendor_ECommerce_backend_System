package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.Seller;
import com.example.ECommerce.Model.User;
import com.example.ECommerce.repositories.OrderRepository;
import com.example.ECommerce.repositories.ProductRepository;
import com.example.ECommerce.repositories.SellerRepository;
import com.example.ECommerce.repositories.UserRepository;
import com.example.ECommerce.services.ProductService;
import com.example.ECommerce.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @Autowired
    private UserRepository userrepository;
    @Autowired
    private SellerRepository sellerrepository;

    private final SellerService sellerservice;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public SellerController(SellerService sellerservice,ProductRepository productRepository,
                            OrderRepository orderRepository) {
        this.sellerservice = sellerservice;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> applyForSeller(Authentication authentication) {

        String userId = authentication.getName(); // JWT subject = userId
        sellerservice.applyForSeller(userId);

        return ResponseEntity.ok("Seller apply request submitted");
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('SELLER')")
    public Map<String, Object> getMySellerProfile(Authentication authentication) {

        String userId = authentication.getName();

        User user = userrepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerrepository.findByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("name",user.getName());
        response.put("email", user.getEmail());
        response.put("role", user.getRole());
        response.put("approved", seller != null && seller.isApproved());

        return response;
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('SELLER')")
    public Map<String, Object> getDashboard(Authentication authentication) {

        String sellerId = authentication.getName();

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalProducts",
                productRepository.findBySellerId(sellerId).size());

        dashboard.put("totalOrders",
                orderRepository.findBySellerId(sellerId).size());

        return dashboard;
    }
}