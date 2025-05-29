package com.example.Order.dto;

import com.example.Order.enums.OrderStatus;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class StatusTimelineDto {
    private UUID orderId;
    private List<StatusEventDto> events;


}
