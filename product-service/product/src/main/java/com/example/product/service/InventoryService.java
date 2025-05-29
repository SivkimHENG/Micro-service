package com.example.product.service;

import com.example.product.dto.AdjustInventoryDto;
import com.example.product.dto.InventoryDto;
import com.example.product.dto.ReservationDto;
import com.example.product.model.Inventory;
import com.example.product.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.collection.internal.StandardOrderedMapSemantics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private final InventoryRepository inventoryRepository;


    public InventoryDto getInventory(UUID productId) {
        Inventory inventory  = inventoryRepository.findProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return toDto(inventory);
    }


    public List<InventoryDto> adjustStock(List<AdjustInventoryDto> adjustments) {

        return adjustments.stream().map(adjust -> {
            Inventory inventory =
                    inventoryRepository.findProductById(adjust.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
            inventory.setQuantity(inventory.getQuantity() + adjust.getDelta());
            return toDto(inventoryRepository.save(inventory));
        }).toList();
    }


    public List<InventoryDto> reservesStock(List<ReservationDto> reservations) {
        return reservations.stream().map(
            reserve -> {
                Inventory inventory = inventoryRepository.findProductById(reserve.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                if(inventory.getQuantity() >= reserve.getQuantity()) {
                    inventory.setQuantity(inventory.getQuantity() - reserve.getQuantity());
                    return toDto(inventoryRepository.save(inventory));
                } else {
                    throw new RuntimeException("Insufficient stock for product " + reserve.getProductId());
                }
            }
        ).toList();

    }


    private InventoryDto toDto(Inventory inventory) {
        InventoryDto dto = new InventoryDto();
        inventory.setProductId(dto.getProductId());
        inventory.setQuantity(dto.getQuantity());
        return dto;
    }

}
