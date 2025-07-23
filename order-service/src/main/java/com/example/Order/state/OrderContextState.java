package com.example.Order.state;

import com.example.Order.enums.OrderStatus;

import java.util.Map;

public class OrderContextState {
   private static final Map<OrderStatus,OrderState> stateMap = Map.of(
           OrderStatus.PENDING, new PendingState(),
           OrderStatus.CONFIRMED, new ConfirmedState(),
           OrderStatus.SHIPPED, new ShippedState(),
           OrderStatus.DELIVERED, new DeliveredState(),
           OrderStatus.CANCELLED, new CancelledState(),
           OrderStatus.RETURNED, new ReturnedState()
   );

    public OrderState getState(OrderStatus status) {
        return stateMap.get(status);
    }

}
