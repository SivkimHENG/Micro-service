package com.example.product.dto;

import ch.qos.logback.core.status.InfoStatus;
import com.example.product.enums.RuleType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
public class PricingRuleDto {
    private UUID id;
    private String ruleName;
    private RuleType ruleType;
    private BigDecimal discountValue;
    private String conditionExpression;
    private UUID productId;
    private Instant createdAt;
    private Instant updatedAt;
    private Long version;
}
