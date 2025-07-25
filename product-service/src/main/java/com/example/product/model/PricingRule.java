package com.example.product.model;

import com.example.product.enums.RuleType;
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
    private String ruleName;

    @Enumerated(EnumType.STRING)
    private RuleType ruleType;

    private BigDecimal discountValue;

    private String conditionExpression;

    private Instant createdAt;
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Version
    private Long version;







}
