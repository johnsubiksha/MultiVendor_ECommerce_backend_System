package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.Wishlist;
import com.example.ECommerce.repositories.ProductRepository;
import com.example.ECommerce.repositories.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Wishlist addToWishlist(@RequestParam String productId,
                                  Authentication authentication) {

        String customerId = authentication.getName();

        Wishlist wishlist = wishlistRepository
                .findByCustomerId(customerId)
                .orElse(new Wishlist());

        wishlist.setCustomerId(customerId);

        if (!wishlist.getProductIds().contains(productId)) {
            wishlist.getProductIds().add(productId);
        }

        return wishlistRepository.save(wishlist);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Product> getWishlist(Authentication authentication) {

        Wishlist wishlist = wishlistRepository
                .findByCustomerId(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Wishlist empty"));

        return productRepository.findAllById(wishlist.getProductIds());
    }


    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Wishlist removeWishlist(@PathVariable String productId,
                                   Authentication authentication) {

        Wishlist wishlist = wishlistRepository
                .findByCustomerId(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Wishlist empty"));

        wishlist.getProductIds().remove(productId);

        return wishlistRepository.save(wishlist);
    }
}