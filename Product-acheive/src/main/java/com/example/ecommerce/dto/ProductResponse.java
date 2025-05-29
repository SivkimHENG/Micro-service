package com.example.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.ecommerce.model.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Boolean isOutOfStock;

    private Long category_id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public ProductResponse(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.isOutOfStock = product.getQuantity() <= 0;
    }

}
