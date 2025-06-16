package com.example.product.dto;

import com.example.product.enums.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ProductDto {

   private UUID id;

   @NotNull
   private String name;
   @NotNull
   private String description;
   @NotNull
   private ProductStatus status;

   private Instant createdAt;
   private Instant updatedAt;

   private Long version;


}
