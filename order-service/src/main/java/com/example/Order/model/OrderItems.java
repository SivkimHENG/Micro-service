package com.example.Order.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.annotation.InterfaceStability;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Setter
@Getter
@RequiredArgsConstructor
@Table(name = "order_items")

public class OrderItems {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  private UUID productId;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private String productSku;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal price;

  @Column(precision = 10, scale = 2)
  private BigDecimal discountAmount;

  @Column(length = 500)
  private String specialInstructions;

}
