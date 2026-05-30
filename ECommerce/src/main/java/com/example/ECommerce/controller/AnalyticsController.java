package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.OrderStatus;
import com.example.ECommerce.Model.Product;
import com.example.ECommerce.Model.Seller;
import com.example.ECommerce.repositories.OrderRepository;
import com.example.ECommerce.repositories.ProductRepository;
import com.example.ECommerce.repositories.SellerRepository;
import com.example.ECommerce.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/analytics")
@PreAuthorize("hasRole('ADMIN')")
public class AnalyticsController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;

    public AnalyticsController(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            SellerRepository sellerRepository,
            UserRepository userRepository) {

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
    }

    // TOTAL REVENUE
    @GetMapping("/revenue")
    public Double getRevenue() {

        return orderRepository.findAll()
                .stream()
                .filter(order ->
                        order.getOrderStatus() == OrderStatus.DELIVERED)
                .mapToDouble(Order::getTotalAmount)
                .sum();
    }

    // DASHBOARD SUMMARY
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> response = new HashMap<>();

        response.put("totalUsers", userRepository.count());
        response.put("totalSellers", sellerRepository.count());
        response.put("totalProducts", productRepository.count());
        response.put("totalOrders", orderRepository.count());

        return response;
    }

    // ORDER STATISTICS
    @GetMapping("/orders")
    public Map<String, Long> orderStats() {

        Map<String, Long> stats = new HashMap<>();

        stats.put("confirmed",
                orderRepository.findAll().stream()
                        .filter(o -> o.getOrderStatus() == OrderStatus.CONFIRMED)
                        .count());

        stats.put("shipped",
                orderRepository.findAll().stream()
                        .filter(o -> o.getOrderStatus() == OrderStatus.SHIPPED)
                        .count());

        stats.put("delivered",
                orderRepository.findAll().stream()
                        .filter(o -> o.getOrderStatus() == OrderStatus.DELIVERED)
                        .count());

        stats.put("cancelled",
                orderRepository.findAll().stream()
                        .filter(o -> o.getOrderStatus() == OrderStatus.CANCELLED)
                        .count());

        return stats;
    }

    // TOP PRODUCTS (LOW STOCK PRODUCTS)
    @GetMapping("/top-products")
    public List<Product> topProducts() {

        return productRepository.findAll()
                .stream()
                .sorted((p1, p2) ->
                        Integer.compare(p1.getStock(), p2.getStock()))
                .limit(10)
                .toList();
    }

    // ALL SELLERS
    @GetMapping("/top-sellers")
    public List<Seller> topSellers() {

        return sellerRepository.findAll();
    }
}