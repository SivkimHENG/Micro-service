package com.example.Order.dto;


import com.example.Order.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {

    private UUID id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private Instant createdAt;
    private Instant updatedAt;
    private List<OrderItemsDto> item ;

}
