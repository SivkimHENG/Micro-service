package com.example.Order.service;

import com.example.Order.assembler.OrderAssembler;
import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.exception.OrderNotFoundException;
import com.example.Order.mapper.*;
import com.example.Order.mapper.updateOrder.UpdateOrderMapper;
import com.example.Order.model.*;
import com.example.Order.repository.OrderRepository;
import com.example.Order.factory.OrderFactory;
import com.example.Order.state.OrderContextState;
import com.example.Order.state.OrderState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderFactory orderFactory;
  private final OrderAssembler orderAssembler;
  private final OrderContextState orderContextState;
  private final UpdateOrderMapper updateOrderMapper;

  private final OrderMapper orderMapper;

  @Override
  @Transactional

  // NOTE: works
  public OrderDto create(OrderDto request) {
    log.info("Creating order from customer:{}", request.customerId());

    Order order = orderFactory.create(request);
    orderAssembler.enrich(order, request);

    Order savedOrder = orderRepository.save(order);
    log.info("Order created Successfully with ID:{} and order number:{}", savedOrder.getId(),
        savedOrder.getOrderNumber());
    return orderMapper.apply(savedOrder);
  }

  //NOTE : WORK
  @Override
  @Transactional
  public OrderDto update(UUID orderId, OrderDto request) {
    log.info("Updating order with ID{}", orderId);

    Order existingOrder = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order ID doesn't exist our record:" + orderId));

    OrderState currentState = orderContextState.getState(existingOrder.getStatus());

    if (request.status() != null && request.status() != existingOrder.getStatus()) {
      if (!currentState.canTransitionTo(request.status())) {
        throw new IllegalStateException("Invalid status transition from "
            + existingOrder.getStatus() + " to " + request.status());
      }
      currentState.apply(existingOrder, request.status(), request.notes());
    }

    updateOrderMapper.updateEntity(existingOrder, request);
    Order updatedOrder = orderRepository.save(existingOrder);

    return orderMapper.apply(updatedOrder);
  }
  //NOTES: WORK
  @Override
  @Transactional
  public OrderDto updateStatus(UUID orderId, OrderStatus newStatus, String reason) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order not found" + orderId));

    OrderState currentState = orderContextState.getState(order.getStatus());
    if (!currentState.canTransitionTo(newStatus)) {
      throw new IllegalStateException("Transition from " + order.getStatus() + " to " + newStatus + " is not allowed.");
    }

    currentState.apply(order, newStatus, reason);
    order.setUpdatedAt(LocalDateTime.now());

    return orderMapper.apply(order);

  }

  @Override
  @Transactional
  public OrderDto updateStatus(UUID orderId, OrderStatus newStatus) {
    return updateStatus(orderId, newStatus, null);
  }




  //NOTE: WORK
  @Override
  public List<OrderStatus> getAllowedStatusTransitions(OrderStatus currentStatus) {
    OrderState state = orderContextState.getState(currentStatus);
    return Arrays.stream(OrderStatus.values())
        .filter(state::canTransitionTo)
        .toList();
  }

  @Override
  public boolean canTransitionTo(OrderStatus from, OrderStatus to) {
    return orderContextState.getState(from).canTransitionTo(to);
  }

  //NOTE: work
  @Override
  public List<OrderDto> findAllOrder() {
    log.debug("Fetching all orders");
    return orderRepository.findAll().stream().map(orderMapper::apply).toList();
  }

  //NOTE : work
  @Override
  public OrderDto findOrderByOrderNumber(String orderNumber) {
    log.debug("Finding order by number: {}", orderNumber);
    Order order = orderRepository.findByOrderNumber(orderNumber)
        .orElseThrow(() -> new OrderNotFoundException("Order number doesn't exist our record: " + orderNumber));
    return orderMapper.apply(order);
  }

  @Override
  @Transactional
  public OrderDto cancel(UUID orderId) {
    log.info("Cancelling order with ID:{} ", orderId);

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order doesn't exist our record :{}" + orderId));

    if (order.getStatus() == OrderStatus.CONFIRMED ||
        order.getStatus() == OrderStatus.CANCELLED) {
      throw new RuntimeException("Cannot cancel order in " + order.getStatus() + " status");
    }

    order.setStatus(OrderStatus.CANCELLED);
    order.setCancelledAt(LocalDateTime.now());
    order.setUpdatedAt(LocalDateTime.now());

    Order cancelledOrder = orderRepository.save(order);
    log.info("Order cancelled successfully with ID: {}", cancelledOrder.getId());

    return orderMapper.apply(cancelledOrder);

  }

  @Override
  @Transactional
  public OrderDto complete(UUID orderId) {
    log.info("Completing order with ID:{}", orderId);
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order doesn't exist in our record :{}" + orderId));

    if (order.getStatus() == OrderStatus.CONFIRMED) {
      throw new RuntimeException("Order is already completed");
    }
    if (order.getStatus() == OrderStatus.CANCELLED) {
      throw new RuntimeException("Cannot complete cancelled order");
    }

    order.setStatus(OrderStatus.CONFIRMED);
    order.setCompleteAt(LocalDateTime.now());
    order.setUpdatedAt(LocalDateTime.now());

    Order completeOrder = orderRepository.save(order);
    log.info("Order completed successfully: {}", completeOrder.getId());

    return orderMapper.apply(completeOrder);
  }

  @Override
  @Transactional
  public void delete(UUID orderId) {
    log.info("Deleting Order with ID:{}", orderId);

    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new OrderNotFoundException("Order number doesn't exist our record:{}" + orderId));

    if (order.getStatus() == OrderStatus.CONFIRMED ||
        order.getStatus() == OrderStatus.SHIPPED) {
      throw new RuntimeException("Cannot delete order in " + order.getStatus() + " status");
    }
    orderRepository.deleteById(orderId);
    log.info("Order deleted successfully with ID:{}", orderId);
  }

}
