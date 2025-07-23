package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;

import java.time.LocalDateTime;

public class ShippedState implements OrderState{


    @Override
    public void apply(Order order, OrderStatus target, String reason) {
        switch (target){
            case DELIVERED ->  {
                order.setStatus(OrderStatus.DELIVERED);
                order.setDeliveredAt(LocalDateTime.now());
            }
            case RETURNED ->  {
                order.setStatus(OrderStatus.RETURNED);
                order.setReturnedAt(LocalDateTime.now());
                order.setNotes("Returned" + reason);
            }
            default -> throw new IllegalStateException("Invalid transition from CONFIRMED to " + target);
        }

        order.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public boolean canTransitionTo(OrderStatus target){
        return  target == OrderStatus.DELIVERED || target == OrderStatus.RETURNED;
    }





}
