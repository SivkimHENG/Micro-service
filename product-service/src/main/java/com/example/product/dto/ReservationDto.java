package com.example.product.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ReservationDto {
    private UUID productId;
    private int quantity;
}
