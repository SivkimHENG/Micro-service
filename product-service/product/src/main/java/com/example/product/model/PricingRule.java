package com.example.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;
    private BigDecimal value;
    private Instant startDate;
    private Instant endDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;








}
