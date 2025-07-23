package com.example.Order.controller;

import com.example.Order.dto.*;
import com.example.Order.model.Order;
import com.example.Order.response.StandardResponse;
import com.example.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
    log.info("Creating new order for customers: {}", orderDto.customerId());
    OrderDto createOrder = orderService.create(orderDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);
  }
//TODO: Some issue occur with orderNumber as String
  @GetMapping("/number/{orderNumber}")
  public ResponseEntity<OrderDto> getOrderByOrderNumber(@PathVariable String orderNumber){
    log.debug("Fetching order by order number:{}",orderNumber);

    OrderDto order = orderService.findOrderByOrderNumber(orderNumber);
    return ResponseEntity.ok(order);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderDto> updateOrder(
          @PathVariable UUID id,
          @RequestBody OrderDto orderDto
  ) {
    log.info("Updating order with ID:{}",id);
    OrderDto updatedOrder = orderService.update(id,orderDto);
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
    log.info("Deleting order with ID:{}", id);
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{id}/cancel")
  public ResponseEntity<OrderDto> cancelOrder(@PathVariable UUID id) {
    log.info("Cancelling order with ID:{}",id);

    OrderDto cancelledOrder =  orderService.cancel(id);
    return ResponseEntity.ok(cancelledOrder);
  }

  @PostMapping("/{id}/complete")
  public ResponseEntity<OrderDto> completeOrder(@PathVariable UUID id) {
    log.info("Completing order with ID:{}", id);
    OrderDto completedOrder = orderService.complete(id);
    return ResponseEntity.ok(completedOrder);
  }



  }








