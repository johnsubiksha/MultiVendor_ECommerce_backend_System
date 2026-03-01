package com.example.ECommerce.Model;

import lombok.Data;

@Data
public class CartItem {
    private String productId;
    private String productName;
    private double price;
    private Integer quantity;
    private double subTotal;
}
