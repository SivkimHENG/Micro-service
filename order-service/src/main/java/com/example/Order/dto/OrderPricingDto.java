package com.example.Order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

/**
 * OrderPricingDto
 */

public record OrderPricingDto(
    BigDecimal subtotal,
    BigDecimal taxAmount,
    BigDecimal DiscountAmount,
    BigDecimal totalAmount,
    String currency ){


}
