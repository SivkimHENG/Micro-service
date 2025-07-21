package com.example.Order.service.Order;

import com.example.Order.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

  OrderDto create(OrderDto request);
  OrderDto update(UUID orderId,OrderDto request);
  OrderDto findOrderById(String orderNumber);
  List<OrderDto> findAll(int page, int size);

  void cancel(UUID orderId);
  void complete(UUID orderId);
  void delete(UUID orderId);


}
