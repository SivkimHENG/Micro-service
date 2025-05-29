package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.enums.ProductStatus;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public ProductDto createProduct(ProductDto dto){
        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return toDto(productRepository.save(product));
    }

    public ProductDto updatedProduct(UUID id, ProductDto dto){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setUpdatedAt(Instant.now());

        return toDto(productRepository.save(product));
    }

    public List<ProductDto> bulkUpsert(List<ProductDto> dtos) {

        List<Product> entities = dtos.stream()
                .map(dto -> {
                    Product product;
                    if(dto.getId() != null) {
                        product = productRepository.findById(dto.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Product not found" + dto.getId()));
                        product.setName(dto.getName());
                        product.setDescription(dto.getDescription());
                    } else {
                        product = new Product();
                        product.setCreatedAt(Instant.now());
                        product.setName(dto.getName());
                        product.setDescription(dto.getDescription());
                        product.setStatus(ProductStatus.ACTIVE);
                    }
                    product.setUpdatedAt(Instant.now());
                    return product;
                }).toList();

        List<Product> savedProduct = productRepository.saveAll(entities);
        return savedProduct.stream().map(this::toDto).toList();

    }

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        return dto;
    }



}
