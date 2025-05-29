package com.example.Order.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemsDraftDto {

    private UUID productId;
    private int quantity;


}
