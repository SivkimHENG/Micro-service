package com.example.Order.service.Order;

import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.exception.IllegalOrderStateException;
import com.example.Order.exception.OrderNotFoundException;
import com.example.Order.exception.ResourceNotFoundException;
import com.example.Order.exception.ValidationException;
import com.example.Order.mapper.OrderMapper;
import com.example.Order.model.Order;
import com.example.Order.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.security.oauthbearer.internals.secured.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;


    @Override
    @Transactional
    public OrderDto create(OrderDto request) {
        log.info("Creating order from customer:{}", request.customerId());

        validateOrderRequest(request);

        Order order = new Order();
        order.setOrderNumber(request.orderNumber());
        order.setCustomerId(request.customerId());
        order.setStatus(request.status());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order.setCompleteAt(null);
        order.setCancelledAt(null);

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {} and number: {}", savedOrder.getId(),savedOrder.getOrderNumber());
        return orderMapper.toDto(savedOrder);
    }

    @Override
    @Transactional
    public OrderDto update(UUID orderId, OrderDto request) {
        log.info("Updating order with ID{}", orderId);

        Order existingOrder = findOrderEntityById(orderId);
        validateOrderCanBeUpdated(existingOrder);
        validateOrderRequest(request);


        orderMapper.updateOrderFromDto(request,existingOrder);
        existingOrder.setUpdatedAt(LocalDateTime.now());

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Order updated successfully {} ", orderId);
        return orderMapper.toDto(updatedOrder);
    }

    @Override
    public OrderDto findOrderById(String orderNumber) {
        log.debug("Finding order by number: {}", orderNumber);
        if(!StringUtils.hasText(orderNumber)) {
            throw new ValidationException("Order number is required");
        }
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException( "Order not found with number: " + orderNumber, orderNumber));

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> findAll(int page, int size) {

        log.debug("Finding all orders - page:{} , size :{}",page,size);
        validatePaginationParams(page,size);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Order> orderPage = orderRepository.findAll(pageable);

        return orderMapper.toDtoList(orderPage.getContent());
    }

    @Override
    @Transactional
    public void cancel(UUID orderId) {

        log.info("Cancelling order with ID:{} ", orderId);
        Order order = findOrderEntityById(orderId);
        validateOrderCanBeCancelled(order);

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        orderRepository.save(order);
        log.info("Order cancelled successfully:{}", orderId);
    }

    @Override
    @Transactional
    public void complete(UUID orderId) {
           log.info("Completing order with ID:{}", orderId);
          Order order = findOrderEntityById(orderId);
          validateOrderCanBeCompleted(order);

          order.setStatus(OrderStatus.CONFIRMED);
          order.setCompleteAt(LocalDateTime.now());
          order.setUpdatedAt(LocalDateTime.now());

          orderRepository.save(order);
          log.info("Order completed successfully: {}", orderId);
    }

    @Override
    @Transactional
    public void delete(UUID orderId) {
        log.info("Deleting Order with ID:{}", orderId);

        Order order  = findOrderEntityById(orderId);
        validateOrderCanBeDeleted(order);;

        orderRepository.delete(order);
        log.info("Order deleted successfully:{}", orderId);
    }

    private Order findOrderEntityById(UUID orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with ID: " + orderId));
    }

    private void validateOrderRequest(OrderDto request) {
        if (request == null) {
            throw new IllegalArgumentException("Order request cannot be null");
        }
        if (request.customerId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (!StringUtils.hasText(request.orderNumber())) {
            throw new IllegalArgumentException("Order number is required");
        }
        // Add more validations as needed
    }

    private void validateOrderCanBeUpdated(Order order) {
        if (order.getStatus() == OrderStatus.CONFIRMED ||
                order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalOrderStateException(
                    "Cannot update order with status: " + order.getStatus());
        }
    }

    private void validateOrderCanBeCancelled(Order order) {
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalOrderStateException(
                    "Cannot cancel confirmed order");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalOrderStateException(
                    "Order is already cancelled");
        }
    }

    private void validateOrderCanBeCompleted(Order order) {
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            throw new IllegalOrderStateException(
                    "Order is already completed");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalOrderStateException(
                    "Cannot complete cancelled order");
        }
    }

    private void validateOrderCanBeDeleted(Order order) {
        if (order.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalOrderStateException(
                    "Only cancelled orders can be deleted. Current status: " + order.getStatus());
        }
    }

    private void validatePaginationParams(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException("Page size must be between 1 and 100");
        }
    }


}

