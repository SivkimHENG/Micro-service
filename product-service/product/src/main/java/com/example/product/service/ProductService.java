package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.enums.ProductStatus;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;



    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::toDto).toList();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    public ProductDto createProduct(ProductDto dto){
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStatus(ProductStatus.ACTIVE);
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        Product saved = productRepository.save(product);
        return toDto(saved);
    }

    public ProductDto updatedProduct(UUID id, ProductDto dto){
        Product product = getProductById(id);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStatus(dto.getStatus());
        product.setUpdatedAt(Instant.now());
        return  toDto(productRepository.save(product));
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setStatus(p.getStatus());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setUpdatedAt(p.getUpdatedAt());
        dto.setVersion(p.getVersion());
        return dto;
    }

}
