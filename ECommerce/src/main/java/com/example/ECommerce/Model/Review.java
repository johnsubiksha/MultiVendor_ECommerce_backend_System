package com.example.ECommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private String productId;
    private String customerId;

    private Integer rating;
    private String reviewText;

    private LocalDateTime createdAt;
}