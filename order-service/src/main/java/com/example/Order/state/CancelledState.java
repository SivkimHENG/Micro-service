package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

public class CancelledState implements OrderState {
    @Override
    public void apply(Order order, OrderStatus target, String reason) {
        throw new IllegalStateException("Cannot transition from CANCELLED to " + target);
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return false;
    }
}
