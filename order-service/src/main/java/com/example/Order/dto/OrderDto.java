package com.example.Order.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.Order.enums.OrderStatus;
import com.example.Order.enums.OrderType;

import com.example.Order.model.Order;
import lombok.Data;

public record OrderDto(
 UUID id,
 String orderNumber,
 UUID customerId,
 OrderStatus status,
 OrderType orderType,
 List<OrderItemDto> items,
 OrderPricingDto orderPricing,
 ShippingAddressDto shippingAddress,
 BillingAddressDto billingAddress,
 LocalDateTime createdAt,
 LocalDateTime updatedAt,
 LocalDateTime completeAt,
 LocalDateTime cancelledAt,
 String notes,
 Long version
) {










 }