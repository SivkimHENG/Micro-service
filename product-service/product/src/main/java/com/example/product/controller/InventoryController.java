package com.example.product.controller;


import com.example.product.dto.AdjustInventoryDto;
import com.example.product.dto.InventoryDto;
import com.example.product.dto.ReservationDto;
import com.example.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getStockStatus(@PathVariable UUID id){
        return ResponseEntity.ok(service.getInventory(id)) ;
    }


    @PostMapping("/adjust")
    public ResponseEntity<List<InventoryDto>> adjustInventory(@RequestBody List<AdjustInventoryDto> adjustments) {
        return ResponseEntity.ok(service.adjustStock(adjustments));
    }


    @PostMapping("/reservations")
    public ResponseEntity<InventoryDto> reserveInventory(List<ReservationDto> reservation) {
        return ResponseEntity.ok((InventoryDto) service.reservesStock(reservation));

    }
}
