package com.example.Order.factory;

import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.mapper.*;
import com.example.Order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor

@Component
public class OrderFactory {

    private final ShippingAddressEntityMapper shippingAddressEntityMapper;
    private final BillingAddressEntityMapper billingAddressEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public Order create(OrderDto dto) {
        Order order = new Order();

        order.setOrderNumber(generateOrderNumber());
        order.setCustomerId(dto.customerId());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderType(dto.orderType());
        order.setCreatedAt(dto.createdAt());
        order.setUpdatedAt(dto.updatedAt());
        order.setNotes(dto.notes());

        return order;
    }

    private String generateOrderNumber(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();

    }




}
