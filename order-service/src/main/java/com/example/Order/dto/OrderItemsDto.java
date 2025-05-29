package com.example.Order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemsDto {

    private UUID id;
    private UUID productId;
    private int quantity;
    private BigDecimal price;

}
