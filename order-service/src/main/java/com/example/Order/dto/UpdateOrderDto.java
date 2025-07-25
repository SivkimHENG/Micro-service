package com.example.Order.dto;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.ShippingAddress;

public record UpdateOrderDto(
         OrderStatus status,
         String reason
) {
}
