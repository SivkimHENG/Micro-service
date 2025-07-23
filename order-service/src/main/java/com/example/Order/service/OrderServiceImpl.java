package com.example.Order.service;

import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.exception.OrderNotFoundException;
import com.example.Order.mapper.*;
import com.example.Order.model.*;
import com.example.Order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final OrderMapper orderMapper;
  private final OrderItemMapper orderItemMapper;
  private final OrderPricingMapper orderPricingMapper;
  private final ShippingAddressMapper shippingAddressMapper;
  private final BillingAddressMapper billingAddressMapper;

  private final OrderItemEntityMapper orderItemEntityMapper;
  private final ShippingAddressEntityMapper shippingAddressEntityMapper;
  private final BillingAddressEntityMapper billingAddressEntityMapper;

  private static final Map<OrderStatus, List<OrderStatus>> STATUS_TRANSITIONS = Map.of(
      OrderStatus.PENDING, Arrays.asList(OrderStatus.CONFIRMED, OrderStatus.CANCELLED),
      OrderStatus.CONFIRMED, Arrays.asList(OrderStatus.SHIPPED, OrderStatus.CANCELLED),
      OrderStatus.SHIPPED, Arrays.asList(OrderStatus.DELIVERED, OrderStatus.RETURNED),
      OrderStatus.DELIVERED, Arrays.asList(OrderStatus.RETURNED),
      OrderStatus.CANCELLED, Arrays.asList(),
      OrderStatus.RETURNED, Arrays.asList());

  @Override
  @Transactional
  public OrderDto create(OrderDto request) {
    log.info("Creating order from customer:{}", request.customerId());

    Order order = new Order();
    order.setOrderNumber(request.orderNumber());
    order.setCustomerId(request.customerId());
    order.setStatus(OrderStatus.PENDING);
    order.setOrderType(request.orderType());
    order.setCreatedAt(request.createdAt());
    order.setUpdatedAt(request.updatedAt());
    order.setNotes(request.notes());

    if (request.shippingAddress() != null) {
      ShippingAddress shippingAddress = shippingAddressEntityMapper
          .toEntity(request.shippingAddress());
      order.setShippingAddress(shippingAddress);
    }

    if (request.billingAddress() != null) {
      BillingAddress billingAddress = billingAddressEntityMapper
          .toEntity(request.billingAddress());
      order.setBillingAddress(billingAddress);
    }

    if (request.items() != null && !request.items().isEmpty()) {
      List<OrderItems> orderItems = request.items().stream()
          .map(orderItemEntityMapper::toEntity)
          .peek(item -> item.setOrder(order))
          .toList();
      order.setItems(orderItems);
    }

    if (request.orderPricing() != null) {
      OrderPricing orderPricing = new OrderPricing();
      orderPricing.setSubtotal(request.orderPricing().subtotal());
      orderPricing.setTaxAmount(request.orderPricing().taxAmount());
      orderPricing.setDiscountAmount(request.orderPricing().DiscountAmount());
      orderPricing.setCurrency(request.orderPricing().currency());
      orderPricing.setTotalAmount(request.orderPricing().totalAmount());
      order.setOrderPricing(orderPricing);
    }

    Order savedOrder = orderRepository.save(order);
    log.info("Order created Successfully with ID:{} and order number:{}", savedOrder.getId(),
        savedOrder.getOrderNumber());

    return orderMapper.apply(savedOrder);
  }

  @Override
    @Transactional
    public OrderDto update(UUID orderId, OrderDto request) {
        log.info("Updating order with ID{}", orderId);

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order ID doesn't exist our record:" + orderId));

        if(existingOrder.getStatus() == OrderStatus.SHIPPED ||
        existingOrder.getStatus() == OrderStatus.DELIVERED ||
        existingOrder.getStatus() == OrderStatus.CANCELLED ||
        existingOrder.getStatus() == OrderStatus.RETURNED) {
            throw new RuntimeException("Cannot update order in " + existingOrder.getStatus() + " status");
        }


        if(request.orderType() != null){
            existingOrder.setOrderType(request.orderType());
        }

        if(request.notes() != null) {
            existingOrder.setNotes(request.notes());
        }

        existingOrder.setUpdatedAt(LocalDateTime.now());

        if(bill)





    }

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
