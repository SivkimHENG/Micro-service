
package com.example.Order.dto;

import lombok.Data;

/**
 * BillingAddressDto
 */
@Data
public class BillingAddressDto {
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String postalCode;
  private String country;

}
