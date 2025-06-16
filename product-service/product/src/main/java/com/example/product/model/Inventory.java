package com.example.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.integration.config.EnableMessageHistory;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private int quantity;
    private int reserve;
    private Instant updatedAt;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;



}
