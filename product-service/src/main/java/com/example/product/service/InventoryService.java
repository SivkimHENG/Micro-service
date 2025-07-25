package com.example.product.service;

import com.example.product.dto.InventoryDto;
import com.example.product.model.Inventory;
import com.example.product.model.Product;
import com.example.product.repository.InventoryRepository;
import com.example.product.repository.ProductRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }
   public InventoryDto createInventory(InventoryDto dto) {
       Product product = productRepository.findById(dto.getProductId())
               .orElseThrow(() -> new RuntimeException("Product Not Found : " + dto.getProductId()));

       Inventory inventory = new Inventory();
       inventory.setProduct(product);
       inventory.setQuantity(dto.getQuantity());
       inventory.setCreatedAt(dto.getCreatedAt());
       inventory.setUpdatedAt(dto.getUpdatedAt());

       Inventory saved = inventoryRepository.save(inventory);
       return toDto(saved);
   }

   public InventoryDto updateInventory(UUID id , InventoryDto dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found : " + id ));

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found : " + dto.getProductId()));

        inventory.setProduct(product);
        inventory.setQuantity(dto.getQuantity());
        inventory.setUpdatedAt(Instant.now());


        Inventory updated = inventoryRepository.save(inventory);
        return toDto(updated);


   }




   private InventoryDto toDto(Inventory inventory){
        InventoryDto dto = new InventoryDto();
        dto.setId(inventory.getId());
        dto.setProductId(inventory.getProduct().getId());
        dto.setQuantity(inventory.getQuantity());
        dto.setCreatedAt(inventory.getCreatedAt());
        dto.setUpdatedAt(inventory.getUpdatedAt());
        return dto;
   }





}
