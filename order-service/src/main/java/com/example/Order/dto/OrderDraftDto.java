package com.example.Order.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderDraftDto {

   private UUID userId;
   private String notes;
   private List<OrderItemsDraftDto> items;



}
