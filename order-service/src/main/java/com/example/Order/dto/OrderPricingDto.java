package com.example.Order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

/**
 * OrderPricingDto
 */

@Data
public class OrderPricingDto {

  private BigDecimal subtotal;
  private BigDecimal taxAmount;
  private BigDecimal DiscountAmount;
  private BigDecimal totalAmount;
  private String currency;

}
