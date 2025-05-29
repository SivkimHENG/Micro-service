package com.example.product.dto;

import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
public class ReservationDto {
    private UUID productId;
    private int quantity;
}
