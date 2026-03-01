package com.example.ECommerce.controller;

import com.example.ECommerce.Model.Cart;
import com.example.ECommerce.Model.CartItem;
import com.example.ECommerce.Model.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.ECommerce.repositories.CartRepository;
import com.example.ECommerce.repositories.OrderRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CartController(CartRepository cartRepository,
                          OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    // ADD TO CART
    @PostMapping("/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Cart addToCart(@RequestBody CartItem item,
                          Authentication authentication) {

        String customerId = authentication.getName();

        Cart cart = cartRepository.findBycustomerId(customerId)
                .orElse(new Cart());

        cart.setCustomerId(customerId);

        item.setSubTotal(item.getPrice() * item.getQuantity());
        cart.getItems().add(item);

        double total = cart.getItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();

        cart.setTotalAmount(total);
        cart.setUpdatedAt(LocalDateTime.now());

        return cartRepository.save(cart);
    }

    // VIEW CART
    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public Cart viewCart(Authentication authentication) {
        return cartRepository.findBycustomerId(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Cart empty"));
    }

    // REMOVE ITEM
    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Cart removeItem(@PathVariable String productId,
                           Authentication authentication) {

        Cart cart = cartRepository.findBycustomerId(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Cart empty"));

        cart.getItems().removeIf(i -> i.getProductId().equals(productId));

        double total = cart.getItems()
                .stream()
                .mapToDouble(CartItem::getSubTotal)
                .sum();

        cart.setTotalAmount(total);

        return cartRepository.save(cart);
    }

    // CHECKOUT
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order checkout(Authentication authentication) {

        String customerId = authentication.getName();

        Cart cart = cartRepository.findBycustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Cart empty"));

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setItems(cart.getItems());
        order.setTotalAmount(cart.getTotalAmount());
        order.setOrderStatus("PLACED");
        order.setPaymentStatus("PENDING");
        order.setOrderedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        cartRepository.delete(cart);

        return savedOrder;
    }
}
