
package com.example.Order.request;

import java.util.List;
import java.util.UUID;

import com.example.Order.dto.BillingAddressDto;
import com.example.Order.dto.ShippingAddressDto;
import com.example.Order.enums.OrderType;

import lombok.Data;

/**
 * CreateOrderRequest
 */

@Data
public class CreateOrderRequest {

  private UUID customerId;
  private OrderType orderType;
  private List<CreateOrderItemRequest> items;
  private ShippingAddressDto shippingAddress;
  private BillingAddressDto billingAddress;
  private String notes;
}
