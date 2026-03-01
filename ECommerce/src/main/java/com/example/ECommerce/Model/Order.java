package com.example.ECommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection="order")
public class Order {
    @Id
    private String id;

    private String customerId;
    private String sellerId;
    private String orderStatus;
    private String paymentStatus;
    private List<CartItem> items;
    private double totalAmount;
    private LocalDateTime orderedAt;
    private LocalDateTime deliveredAt;

}
