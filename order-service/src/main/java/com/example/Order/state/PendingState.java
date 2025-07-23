package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.exception.IllegalOrderStateException;
import com.example.Order.model.Order;

import java.time.LocalDateTime;

public class PendingState implements OrderState {

  @Override
  public void apply(Order order, OrderStatus target, String reason) {
    switch (target) {
      case CONFIRMED -> {
        order.setStatus(OrderStatus.CONFIRMED);
        order.setCompleteAt(LocalDateTime.now());
      }
      case CANCELLED -> {
        order.setStatus(OrderStatus.CANCELLED);
        order.setCompleteAt(LocalDateTime.now());
        order.setNotes("Cancelled note" + reason);
      }
      default -> throw new IllegalStateException("Invalid transition");
    }

    order.setUpdatedAt(LocalDateTime.now());
  }

  @Override
  public boolean canTransitionTo(OrderStatus target) {
    return target == OrderStatus.CONFIRMED || target == OrderStatus.CANCELLED;
  }

}
