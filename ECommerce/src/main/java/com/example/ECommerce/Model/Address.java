package com.example.ECommerce.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "addresses")
@Data
public class Address {

    @Id
    private String id;

    private String customerId;

    private String fullName;
    private String phone;

    private String street;
    private String city;
    private String state;
    private String zipCode;
}
