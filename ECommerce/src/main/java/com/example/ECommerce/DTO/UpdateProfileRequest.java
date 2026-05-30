package com.example.ECommerce.DTO;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;
    private int age;
    private String phone;
}