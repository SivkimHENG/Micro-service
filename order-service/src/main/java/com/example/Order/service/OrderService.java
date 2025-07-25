package com.example.Order.service;

import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {

  OrderDto create(OrderDto request);
  OrderDto update(UUID orderId,OrderDto request);
  OrderDto findOrderByOrderNumber(String orderNumber);
  OrderDto cancel(UUID orderId);
  OrderDto complete(UUID orderId);
  void delete(UUID orderId);

  List<OrderDto> findAllOrder();

  OrderDto updateStatus(UUID orderId, OrderStatus newStatus);
  OrderDto updateStatus(UUID orderId, OrderStatus newStatus, String reason);
  List<OrderStatus>  getAllowedStatusTransitions(OrderStatus currentStatus);
  boolean canTransitionTo(OrderStatus from , OrderStatus to);

}
