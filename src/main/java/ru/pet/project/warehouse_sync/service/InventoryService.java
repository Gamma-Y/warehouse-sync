package ru.pet.project.warehouse_sync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.pet.project.warehouse_sync.db.repository.InventoryRepository;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;


/**
 * @author Gamma on 06.02.2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;


    public InventoryDto getInventory() {
        InventoryDto inventoryDto = new InventoryDto();
        return inventoryDto;
    }

}
