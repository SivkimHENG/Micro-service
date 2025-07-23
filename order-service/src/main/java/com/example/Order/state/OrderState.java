package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

public interface OrderState {

    void apply(Order order, OrderStatus target, String reason);
    boolean canTransitionTo(OrderStatus target);


}
