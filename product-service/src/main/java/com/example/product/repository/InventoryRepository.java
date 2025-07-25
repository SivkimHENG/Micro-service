package com.example.product.repository;

import com.example.product.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory,UUID> {
    Optional<Inventory> findProductById(UUID id);

}
