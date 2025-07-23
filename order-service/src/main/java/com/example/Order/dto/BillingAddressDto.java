
package com.example.Order.dto;

import lombok.Data;

/**
 * BillingAddressDto
 */
public record BillingAddressDto (
   String recipientName,
   String addressLine1,
   String addressLine2,
   String city,
   String state,
   String postalCode,
   String country
){
}
