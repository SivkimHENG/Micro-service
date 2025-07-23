package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

import java.time.LocalDateTime;

public class ConfirmedState implements  OrderState {

    @Override
    public void apply(Order order, OrderStatus target, String reason) {
        switch (target) {
            case SHIPPED -> {
                order.setStatus(OrderStatus.SHIPPED);
                order.setShippedAt(LocalDateTime.now());
            }
            case CANCELLED -> {
                order.setStatus(OrderStatus.CANCELLED);
                order.setCancelledAt(LocalDateTime.now());
            }
            default -> throw new IllegalStateException("Invalid transition from CONFIRMED to " + target);
        }
        order.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return target == OrderStatus.SHIPPED || target == OrderStatus.CANCELLED;
    }


}
