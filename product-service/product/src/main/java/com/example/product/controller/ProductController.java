package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        List<ProductDto> products = service.getAllProducts();
         return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.toDto(service.getProductById(id)));

    }
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        log.info("Receive : {}", dto);
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable UUID id, @RequestBody ProductDto dto) {
        ProductDto updated = service.updatedProduct(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }




}
