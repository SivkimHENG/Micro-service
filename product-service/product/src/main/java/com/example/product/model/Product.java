package com.example.product.model;


import com.example.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name =  "products")
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


}
