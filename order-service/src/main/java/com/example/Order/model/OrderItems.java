package com.example.Order.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
@Data
@Table (name = "order_items")
@Setter
@Getter
@RequiredArgsConstructor



public class OrderItems {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    private int  quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;




}
