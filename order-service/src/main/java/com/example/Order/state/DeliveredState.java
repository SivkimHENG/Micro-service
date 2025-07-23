package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

import java.time.LocalDateTime;

public class DeliveredState implements OrderState {

    @Override
    public void apply(Order order, OrderStatus target, String reason) {

        if(target == OrderStatus.RETURNED) {
            order.setStatus(OrderStatus.RETURNED);
            order.setDeliveredAt(LocalDateTime.now());
            order.setNotes("Returned after delivery: " + reason);
            order.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new IllegalStateException("Invalid transition from DELIVERED to " + target));
        }


    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return target == OrderStatus.RETURNED;
    }



}
