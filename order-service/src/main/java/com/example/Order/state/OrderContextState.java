package com.example.Order.state;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public final class OrderContextState {
   private static final Map<OrderStatus,OrderState> stateMap = Map.of(
           OrderStatus.PENDING, new PendingState(),
           OrderStatus.CONFIRMED, new ConfirmedState(),
           OrderStatus.SHIPPED, new ShippedState(),
           OrderStatus.DELIVERED, new DeliveredState(),
           OrderStatus.CANCELLED, new CancelledState(),
           OrderStatus.RETURNED, new ReturnedState()
   );

    private OrderContextState(){}

    public OrderState getState(OrderStatus status) {
        OrderState state = stateMap.get(status);
        if(state == null) {
            throw new IllegalArgumentException("Unsupported OrderStatus:" + status);
        }
        return state;
    }
}
