
package com.example.Order.dto;

import lombok.Data;

/**
 * ShippingAddressDto
 */
public record ShippingAddressDto (
   String recipientName,
   String addressLine1,
   String addressLine2,
   String city,
   String state,
   String postalCode,
   String country,
   String phoneNumber
   ) {
}
