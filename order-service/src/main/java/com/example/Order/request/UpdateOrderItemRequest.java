
package com.example.Order.request;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

/**
 * UpdateOrderItemRequest
 */
@Data
public class UpdateOrderItemRequest {

  private UUID id;

  private Integer quantity;

  private BigDecimal price;

  private BigDecimal discountAmount;

  private String specialInstructions;

}
