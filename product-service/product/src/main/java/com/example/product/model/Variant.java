package com.example.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "variants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String sku;
    private BigDecimal price;
    private String title;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;









}
