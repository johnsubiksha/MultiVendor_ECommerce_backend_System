package com.example.ECommerce.controller;

import com.example.ECommerce.Model.CartItem;
import com.example.ECommerce.Model.Order;
import com.example.ECommerce.repositories.OrderRepository;
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

    // BUY NOW
    @PostMapping("/buy-now")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order buyNow(@RequestBody CartItem item,
                        Authentication authentication) {

        String customerId = authentication.getName();

        item.setSubTotal(item.getPrice() * item.getQuantity());

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(List.of(item));
        order.setTotalAmount(item.getSubTotal());
        order.setOrderStatus("PLACED");
        order.setPaymentStatus("PENDING");
        order.setOrderedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // VIEW MY ORDERS
    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public List<Order> getMyOrders(Authentication authentication) {
        return orderRepository.findBycustomerId(authentication.getName());
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