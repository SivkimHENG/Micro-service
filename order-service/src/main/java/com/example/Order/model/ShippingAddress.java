package com.example.Order.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
public class ShippingAddress {
  @Column(nullable = false)
  private String recipientName;

  @Column(insertable = false, updatable = false)
  private String addressLine1;

  @Column(insertable = false,updatable = false)
  private String addressLine2;

  @Column(insertable = false, updatable = false)
  private String city;

  @Column(insertable = false, updatable = false)
  private String state;

  @Column(insertable = false, updatable = false)
  private String postalCode;

  @Column(insertable = false, updatable = false)
  private String country;

  private String phoneNumber;
}
