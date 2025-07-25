package com.example.product.model;


import com.example.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;


    @Enumerated(EnumType.STRING)
    private ProductStatus status;


    private Instant createdAt;
    private Instant updatedAt;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Inventory> inventories;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<PricingRule> pricingRules;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Variant> variants;

    @Version
    private Long version;



}
