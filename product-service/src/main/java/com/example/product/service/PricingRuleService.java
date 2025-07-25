package com.example.product.service;


import com.example.product.dto.PricingRuleDto;
import com.example.product.model.PricingRule;
import com.example.product.model.Product;
import com.example.product.repository.InventoryRepository;
import com.example.product.repository.PricingRuleRepository;
import com.example.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PricingRuleService {

    private final PricingRuleRepository pricingRuleRepository ;
    private final ProductRepository productRepository;


    public PricingRuleService(PricingRuleRepository pricingRuleRepository, ProductRepository productRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
        this.productRepository = productRepository;
    }

    public PricingRuleDto create(PricingRuleDto dto) {
        PricingRule rule = new PricingRule();
        rule.setRuleName(dto.getRuleName());
        rule.setRuleType(dto.getRuleType());
        rule.setDiscountValue(dto.getDiscountValue());
        rule.setConditionExpression(dto.getConditionExpression());


        if(dto.getProductId() != null ) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product Not Found"));
            rule.setProduct(product);
        }

        rule.setCreatedAt(Instant.now());
        rule.setUpdatedAt(Instant.now());

        return toDto(pricingRuleRepository.save(rule));
    }


    public BigDecimal applyPricingRules(Product product, BigDecimal basePrice) {
        List<PricingRule> rules = pricingRuleRepository.findByProductId(product.getId());


        for(PricingRule rule : rules) {
            switch (rule.getRuleType()) {
                case FIXED_DISCOUNT -> basePrice = basePrice.subtract(rule.getDiscountValue());
                case PERCENTAGE_DISCOUNT -> basePrice = basePrice.multiply(BigDecimal.ONE.subtract(rule.getDiscountValue()));
                case FLAT_PRICE_OVERRIDE -> basePrice = rule.getDiscountValue();
            }
        }

        return basePrice.max(BigDecimal.ZERO);

    }







    public List<PricingRuleDto> getProductById(UUID id) {
        return pricingRuleRepository.findById(id).stream().map(this::toDto).toList();
    }

    public PricingRuleDto update(UUID id , PricingRuleDto dto) {
        PricingRule rule = pricingRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PricingRule Not Found"));

        rule.setRuleName(dto.getRuleName());
        rule.setRuleType(dto.getRuleType());
        rule.setDiscountValue(dto.getDiscountValue());
        rule.setConditionExpression(dto.getConditionExpression());

        if(dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            rule.setProduct(product);
        }

        rule.setUpdatedAt(Instant.now());

        return toDto(pricingRuleRepository.save(rule));

    }

    public void delete(UUID id) {
        pricingRuleRepository.deleteById(id);
    }


    private PricingRuleDto toDto(PricingRule rule) {
        PricingRuleDto dto = new PricingRuleDto();
        dto.setId(rule.getId());
        dto.setRuleName(rule.getRuleName());
        dto.setRuleType(rule.getRuleType());
        dto.setDiscountValue(rule.getDiscountValue());
        dto.setConditionExpression(rule.getConditionExpression());
        dto.setProductId(rule.getProduct() != null ? rule.getProduct().getId() : null);
        dto.setCreatedAt(rule.getCreatedAt());
        dto.setUpdatedAt(rule.getUpdatedAt());
        dto.setVersion(rule.getVersion());
        return dto;

    }


}
