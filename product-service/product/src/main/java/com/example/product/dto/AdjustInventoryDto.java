package com.example.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AdjustInventoryDto {
    private UUID productId;
    private int delta;
}
