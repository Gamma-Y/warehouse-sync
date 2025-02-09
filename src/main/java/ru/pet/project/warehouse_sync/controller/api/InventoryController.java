package ru.pet.project.warehouse_sync.controller.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.project.warehouse_sync.service.InventoryService;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Gamma on 09.02.2025
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/inventories")
public class InventoryController {

    private final InventoryService inventoryService;


    @GetMapping
    public ResponseEntity<Collection<InventoryDto>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryService.getInventory(id));
    }

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDto));
    }

    @PutMapping
    public ResponseEntity<InventoryDto> updateInventory(@RequestBody InventoryDto inventoryDto) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InventoryDto> deleteInventory(@PathVariable UUID id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }


}
