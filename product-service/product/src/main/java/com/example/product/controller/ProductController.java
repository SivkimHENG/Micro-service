package com.example.product.controller;


import com.example.product.dto.ProductDto;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto) {
        Product createdProduct = service.createProduct(dto);

        ProductDto response = new ProductDto();
        response.setName(createdProduct.getName());
        response.setDescription(createdProduct.getDescription());
        response.setCreatedAt(createdProduct.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto>
    updateProduct(@PathVariable UUID id, @RequestBody ProductDto dto
    ) {
        Product updateProduct = service.updatedProduct(id,dto);

        ProductDto response = new ProductDto();
        response.setName(updateProduct.getName());
        response.setDescription(updateProduct.getDescription());
        response.setUpdatedAt(updateProduct.getUpdatedAt());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<ProductDto>> bulkUpsert(
            @RequestBody List<ProductDto> dto
    ) {
        List<ProductDto> savedProduct = service.bulkUpsert(dto);
        return ResponseEntity.ok(savedProduct);
    }



}
