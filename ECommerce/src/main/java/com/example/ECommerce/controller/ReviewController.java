package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Review;
import com.example.ECommerce.repositories.ReviewRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Review addReview(@PathVariable String productId,
                            @RequestBody Review review,
                            Authentication authentication) {

        review.setProductId(productId);
        review.setCustomerId(authentication.getName());
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @GetMapping("/{productId}")
    public List<Review> getReviews(@PathVariable String productId) {

        return reviewRepository.findByProductId(productId);
    }
}