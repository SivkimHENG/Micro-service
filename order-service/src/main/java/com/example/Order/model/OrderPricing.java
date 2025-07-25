package com.example.Order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPricing {

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal subtotal;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal taxAmount;

  @Column(precision = 10, scale = 2)
  private BigDecimal discountAmount;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalAmount;

  @Column(nullable = false, length = 3)
  private String currency;

}
