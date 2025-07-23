package com.example.Order.dto;

import com.example.Order.enums.OrderType;

import java.util.List;
import java.util.UUID;

public record CreateOrderDto(
        UUID customerId,
        OrderType orderType,
        List<OrderItemDto> items,
        ShippingAddressDto shippingAddress,
        BillingAddressDto billingAddress,
        String notes
) { }
