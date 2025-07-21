
package com.example.Order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

/**
 * OrderItemDto
 */
@Data
public class OrderItemDto {

  private UUID id;
  private UUID productId;
  private String productName;
  private String productSku;
  private int quantity;
  private BigDecimal price;
  private BigDecimal discountAmount;
  private String specialInstruction;

}
