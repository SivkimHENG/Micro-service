package com.example.Order.controller;

import com.example.Order.dto.*;
import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;
import com.example.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/orders")
@Slf4j
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto request) {
    OrderDto created = orderService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }

  @PutMapping("/{orderId}")
  public ResponseEntity<OrderDto> updatedOrder(
          @PathVariable UUID orderId,
          @RequestBody OrderDto request
  ) {
    OrderDto updated = orderService.update(orderId,request);
    return ResponseEntity.ok(updated);
  }

  @PutMapping("/{orderId}/status")
  public ResponseEntity<OrderDto> updateOrderStatus(
          @PathVariable UUID orderId,
          @RequestBody UpdateOrderDto updateOrderDto
        ){
    OrderDto updated = orderService.updateStatus(orderId,updateOrderDto.status(), updateOrderDto.reason());
    return ResponseEntity.ok(updated);
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> findAllOrders() {
    List<OrderDto> orders =  orderService.findAllOrder();
    return ResponseEntity.ok(orders);
  }


  @GetMapping("/{orderNumber}")
  public ResponseEntity<OrderDto> getOrderByOrderNumber(@PathVariable String orderNumber){
    OrderDto order = orderService.findOrderByOrderNumber(orderNumber);
    return ResponseEntity.ok(order);
  }

  @GetMapping("/transitions")
  public ResponseEntity<List<OrderStatus>> getAllowStatusTransitions(
          @RequestParam OrderStatus currentStatus
  ){
    return ResponseEntity.ok(orderService.getAllowedStatusTransitions(currentStatus));
  }

  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> deleteOrder(@PathVariable UUID orderId) {
    orderService.delete(orderId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<OrderDto> cancelOrder(@PathVariable UUID orderId) {
    OrderDto cancelled =  orderService.cancel(orderId);
    return ResponseEntity.ok(cancelled);
  }

  @PostMapping("/{orderId}/complete")
  public ResponseEntity<OrderDto> completeOrder(@PathVariable UUID orderId) {
    OrderDto completed = orderService.complete(orderId);
    return ResponseEntity.ok(completed);
  }






  }








