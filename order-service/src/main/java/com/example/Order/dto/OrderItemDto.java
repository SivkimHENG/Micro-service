
package com.example.Order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * OrderItemDto
 */

public record OrderItemDto(
    UUID id,
    UUID productId,
    String productName,
    String productSku,
    int quantity,
    BigDecimal price,
    BigDecimal discountAmount,
    String specialInstruction) {

}
