package com.example.product.dto;

import com.example.product.enums.ProductStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ProductDto {

   private UUID id;
   private String name;
   private String description;
   private ProductStatus status;

   private Instant createdAt;
   private Instant updatedAt;



}
