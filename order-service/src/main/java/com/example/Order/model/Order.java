package com.example.Order.model;

import com.example.Order.enums.OrderStatus;
import com.example.Order.enums.OrderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String orderNumber;

  @Column(nullable = false)
  private UUID customerId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderStatus status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private OrderType orderType;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderItems> items = new ArrayList<>();

  @Embedded
  private OrderPricing orderPricing;

  @Embedded
  private ShippingAddress shippingAddress;

  @Embedded
  private BillingAddress billingAddress;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  private LocalDateTime completeAt;
  private LocalDateTime shippedAt;
  private LocalDateTime deliveredAt;
  private LocalDateTime returnedAt;
  private LocalDateTime cancelledAt;

  @Column(length = 100)
  private String notes;

  @Version
  private Long version;
}

