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

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItems> items = new ArrayList<>();

  @Embedded
  private OrderPricing orderPricing;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "recipientName", column = @Column(name = "billing_recipient_name")),
    @AttributeOverride(name = "addressLine1", column = @Column(name = "billing_address_line1")),
    @AttributeOverride(name = "addressLine2", column = @Column(name = "billing_address_line2")),
    @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
    @AttributeOverride(name = "state", column = @Column(name = "billing_state")),
    @AttributeOverride(name = "postalCode", column = @Column(name = "billing_postal_code")),
    @AttributeOverride(name = "country", column = @Column(name = "billing_country"))
  })
  private BillingAddress billingAddress;


  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "recipientName", column = @Column(name = "shipping_recipient_name")),
          @AttributeOverride(name = "addressLine1", column = @Column(name = "shipping_address_line1")),
          @AttributeOverride(name = "addressLine2", column = @Column(name = "shipping_address_line2")),
          @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
          @AttributeOverride(name = "state", column = @Column(name = "shipping_state")),
          @AttributeOverride(name = "postalCode", column = @Column(name = "shipping_postal_code")),
          @AttributeOverride(name = "country", column = @Column(name = "shipping_country")),
          @AttributeOverride(name = "phoneNumber", column = @Column(name = "shipping_phone_number"))
  })
  private ShippingAddress shippingAddress;



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

