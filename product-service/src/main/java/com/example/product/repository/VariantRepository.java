package com.example.product.repository;

import com.example.product.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VariantRepository extends JpaRepository<Variant, UUID> {

}
