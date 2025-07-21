package com.example.Order.controller;

import com.example.Order.dto.*;
import com.example.Order.response.StandardResponse;
import com.example.Order.service.Order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/orders")
@Validated
@Slf4j
public class OrderController {

  private final OrderService orderService;


  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
    log.info("Creating new order for customers: {}", orderDto.customerId());
    OrderDto createOrder = orderService.create(orderDto);

    URI location = URI.create("v1/api/orders/" + createOrder.id());
    return ResponseEntity.created(location).body(createOrder);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable String orderNumber){
    log.debug("Fetching order by order number:{}",orderNumber);
    return ResponseEntity.ok(orderService.findOrderById(orderNumber));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderDto> updateOrder(@PathVariable UUID id, @RequestBody OrderDto orderDto ) {
    log.info("Updating order with ID:{}",id);
    return ResponseEntity.ok(orderService.update(id,orderDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
    log.info("Deleting order with ID:{}", id);
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }


  @PostMapping("/{id}/cancel")
  public ResponseEntity<StandardResponse> cancelOrder(@PathVariable UUID id) {
    log.info("Cancelling order with ID:{}",id);
    orderService.cancel(id);
    return ResponseEntity.ok(StandardResponse.success("Order cancelled"));
  }

  @PostMapping("/{id}/complete")
  public ResponseEntity<StandardResponse> completeOrder(@PathVariable UUID id) {
    log.info("Completing order with ID:{}", id);
    orderService.complete(id);
    return ResponseEntity.ok(StandardResponse.success("Order Completed"));
  }








}
