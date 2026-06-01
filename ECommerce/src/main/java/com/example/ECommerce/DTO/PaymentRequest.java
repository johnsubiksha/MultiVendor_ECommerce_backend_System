package com.example.ECommerce.DTO;

import com.example.ECommerce.Enum.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private PaymentMethod paymentMethod;
}
