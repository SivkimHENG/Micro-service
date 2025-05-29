package com.example.Order.controller;


import com.example.Order.dto.*;
import com.example.Order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.BorderUIResource;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/order")
public class OrderController {

    private final OrderService service;


    @PostMapping("/drafts")
    public ResponseEntity<OrderDto> createDraft(@RequestBody OrderDraftDto draft) {
        OrderDto order = service.createDraft(draft);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


    @PostMapping("/{id}/place")
    public ResponseEntity<OrderDto> orderPlaced(@PathVariable UUID orderId) {
        OrderDto order =  service.placeOrder(orderId);
        return ResponseEntity.ok(order);
    }
    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderDto> cancelOrder(
            @PathVariable UUID orderId,
            @RequestBody CancellationDto cancel
            ) {
        OrderDto order = service.cancelOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<StatusTimelineDto> getStatustimeline(@PathVariable UUID id) {
        StatusTimelineDto timeline = service.getStatusTimeline(id);
        return ResponseEntity.ok(timeline);
    }


    @PostMapping("/{id}/retry-payment")
    public ResponseEntity<OrderDto> retryPayment(@PathVariable UUID orderId) {

        OrderDto order = service.retryPayment(orderId);
        return ResponseEntity.ok(order);
    }

}
