package com.example.Order.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

/**
 * CreateOrderItemRequest
 */

@Data
public class CreateOrderItemRequest {
  private UUID productId;
  private String productName;
  private String productSku;
  private int quantity;
  private BigDecimal price;
  private BigDecimal discountAmount;
  private String specialInstructions;
}
