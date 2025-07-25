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

  @Column(nullable = false )
  private String addressLine1;

  @Column(nullable = false)
  private String addressLine2;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  private String postalCode;

  @Column(nullable = false)
  private String country;

  private String phoneNumber;
}
