package com.example.product.repository;

import com.example.product.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PricingRuleRepository extends JpaRepository<PricingRule, UUID> {
}
