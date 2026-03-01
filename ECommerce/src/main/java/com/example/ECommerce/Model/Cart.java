package com.example.ECommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;

    private String customerId;
    private List<CartItem> items = new ArrayList<>();
    private Double totalAmount;
    private LocalDateTime updatedAt;
}
