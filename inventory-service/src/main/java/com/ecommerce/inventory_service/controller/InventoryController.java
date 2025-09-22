package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;


    @GetMapping
    public ResponseEntity<Boolean> isInStock(@RequestParam String skuCode, @RequestParam Integer quantity){
        boolean isInStock = inventoryService.isInStock(skuCode, quantity);

        return isInStock ? ResponseEntity.ok(true) : ResponseEntity.noContent().build();
    }
}
