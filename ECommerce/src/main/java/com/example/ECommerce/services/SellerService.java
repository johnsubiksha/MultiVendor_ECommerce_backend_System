package com.example.ECommerce.services;

import com.example.ECommerce.Model.Seller;
import com.example.ECommerce.repositories.SellerRepository;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public void applyForSeller(String userId) {

        Seller existing = sellerRepository.findByUserId(userId);
        if (existing != null) {
            throw new RuntimeException("Already applied");
        }

        Seller seller = new Seller();
        seller.setUserId(userId);
        seller.setApproved(false);

        sellerRepository.save(seller);
    }

}