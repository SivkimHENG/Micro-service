
package com.example.Order.request;

import java.util.List;

import com.example.Order.enums.OrderStatus;

import lombok.Data;

/**
 * UpdateOrderRequest
 */
@Data
public class UpdateOrderRequest {

  private OrderStatus status;
  private List<UpdateOrderItemRequest> items;
  private String note;

}
