package com.example.ECommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection="wishlists")
public class Wishlist {
    @Id
    private String id;

    private String customerId;

    private List<String> productIds = new ArrayList<>();

}
