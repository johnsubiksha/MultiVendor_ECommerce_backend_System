package com.example.ECommerce.controller;

import com.example.ECommerce.DTO.BuyNowRequest;
import com.example.ECommerce.Model.*;
import com.example.ECommerce.Model.PaymentStatus;
import com.example.ECommerce.repositories.AddressRepository;
import com.example.ECommerce.repositories.OrderRepository;
import com.example.ECommerce.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    // BUY NOW
    @PostMapping("/buy-now")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order buyNow(@RequestBody BuyNowRequest request,
                        Authentication authentication) {

        String customerId = authentication.getName();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Address address = addressRepository.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        CartItem item = new CartItem();

        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setPrice(product.getPrice());
        item.setQuantity(request.getQuantity());

        item.setSubTotal(item.getPrice() * item.getQuantity());

        Order order = new Order();

        order.setCustomerId(customerId);
        order.setItems(List.of(item));
        order.setTotalAmount(item.getSubTotal());

        order.setOrderStatus("PLACED");
        order.setAddress(address);

        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setOrderedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // VIEW MY ORDERS
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Order> getMyOrders(Authentication authentication) {

        return orderRepository.findByCustomerId(authentication.getName());
    }

    // UPDATE STATUS (SELLER)
    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('SELLER')")
    public Order updateStatus(@PathVariable String orderId,
                              @RequestParam String status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        order.setOrderStatus(status);

        if ("DELIVERED".equals(status)) {
            order.setDeliveredAt(LocalDateTime.now());
        }

        return orderRepository.save(order);
    }
}