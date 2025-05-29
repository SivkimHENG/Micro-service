package com.example.product.dto;

import com.example.product.model.Product;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class InventoryDto {
   private UUID productId;
   private int quantity;
}
