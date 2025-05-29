package com.example.Order.dto;


import com.example.Order.enums.OrderStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class StatusEventDto {

    private OrderStatus status;
    private Instant timestamp;
    private String note;
}


