package com.example.ECommerce.controller;

import com.example.ECommerce.DTO.PaymentRequest;
import com.example.ECommerce.Model.Order;
import com.example.ECommerce.Model.PaymentMethod;
import com.example.ECommerce.Model.PaymentStatus;
import com.example.ECommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private OrderRepository orderRepository;

    //pay
    @PostMapping("/pay")
    @PreAuthorize("hasRole('CUSTOMER')")
    public Order pay(@RequestBody PaymentRequest request, Authentication authentication) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(()-> new RuntimeException("no order found"));

        if(!(order.getCustomerId().equals(authentication.getName()))) {
            throw new RuntimeException("Unauthorized access");
        }

        if(order.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw  new RuntimeException("Already paid");
        }

        order.setPaymentMethod(request.getPaymentMethod());

        if(PaymentMethod.COD == request.getPaymentMethod()) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }
        else {
            order.setPaymentStatus(PaymentStatus.SUCCESS);
            order.setPaidAt(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }

}
