package com.example.ECommerce.controller;

import com.example.ECommerce.DTO.BuyNowRequest;
import com.example.ECommerce.Model.*;
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

        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setAddress(address);

        order.setPaymentStatus("PENDING");
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
                              @RequestParam OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        order.setOrderStatus(status);

        if (OrderStatus.DELIVERED.equals(status)) {
            order.setDeliveredAt(LocalDateTime.now());
        }

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order cancelOrder(@PathVariable String orderId,
                             Authentication authentication) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Customer can cancel only their own order
        if (!order.getCustomerId().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized");
        }

        // Cannot cancel delivered orders
        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be cancelled");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}/return")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order returnOrder(@PathVariable String orderId,
                             Authentication authentication) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getCustomerId().equals(authentication.getName())) {
            throw new RuntimeException("Unauthorized");
        }

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException(
                    "Only delivered orders can be returned");
        }

        order.setOrderStatus(OrderStatus.RETURN_REQUESTED);

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}/approve-return")
    @PreAuthorize("hasRole('SELLER')")
    public Order approveReturn(@PathVariable String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.RETURN_REQUESTED) {
            throw new RuntimeException("No return request found");
        }

        order.setOrderStatus(OrderStatus.RETURNED);

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}/reject-return")
    @PreAuthorize("hasRole('SELLER')")
    public Order rejectReturn(@PathVariable String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.RETURN_REQUESTED) {
            throw new RuntimeException("No return request found");
        }

        order.setOrderStatus(OrderStatus.RETURN_REJECTED);

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}/mark-returned")
    @PreAuthorize("hasRole('SELLER')")
    public Order markReturned(@PathVariable String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.RETURN_APPROVED) {
            throw new RuntimeException("Return not approved");
        }

        order.setOrderStatus(OrderStatus.RETURNED);

        return orderRepository.save(order);
    }
}