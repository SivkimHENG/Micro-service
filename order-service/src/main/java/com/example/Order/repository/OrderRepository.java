package com.example.Order.repository;

import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

  Optional <Order> findByOrderNumber(String orderNumber);

  List<Order> findByCustomerId(UUID customerId);

  List<Order> findByCustomerIdAndStatus(UUID customerId, OrderStatus orderStatus);

  List<Order> findByStatusAndCreatedAtBetween(OrderStatus orderStatus, LocalDateTime start, LocalDateTime end);

// @Query("SELECT o FROM order o WHERE o.status = :status AND o.createdAt < :cutoffDate")
//List<Order> findStaleOrders(@Param("status") OrderStatus status,
//     @Param("cutoffDate") LocalDateTime cutoffDate);

// @Query("SELECT COUNT(o) FROM Order o WHERE o.customerId = :customerId AND o.status NOT IN :excludedStatuses")
// long countActiveOrdersByCustomer(@Param("customerId") UUID customerId,
//     @Param("excludedStatuses") List<OrderStatus> excludedStatuses);
}
