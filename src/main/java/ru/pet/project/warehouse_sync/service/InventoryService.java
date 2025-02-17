package ru.pet.project.warehouse_sync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pet.project.warehouse_sync.db.entity.InventoryItem;
import ru.pet.project.warehouse_sync.db.repository.InventoryRepository;
import ru.pet.project.warehouse_sync.exeption.ResourceNotFoundException;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;
import ru.pet.project.warehouse_sync.service.mapper.InventoryMapper;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * @author Gamma on 06.02.2025
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic.notification.low}")
    private String lowTopicNotification;

    @Transactional(readOnly = true)
    public Collection<InventoryDto> getItems() {
        log.info("Fetching all inventory");
        List<InventoryItem> all = inventoryRepository.findAll();
        return inventoryMapper.toDtos(all);
    }

    @Transactional(readOnly = true)
    public InventoryDto getItem(UUID id) {
        log.info("Fetching inventory with id: {}", id);
        InventoryItem inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", id));
        return inventoryMapper.toDto(inventoryItem);

    }


    public InventoryDto createItem(InventoryDto inventoryDto) {
        log.info("Creating inventory: {}", inventoryDto);
        InventoryItem inventoryItem = inventoryMapper.toEntity(inventoryDto);
        inventoryItem = inventoryRepository.save(inventoryItem);
        return inventoryMapper.toDto(inventoryItem);
    }

    @Transactional
    public InventoryDto updateItem(InventoryDto inventoryDto) {
        UUID id = inventoryDto.id();
        log.info("Updating inventory with id: {}", id);
        InventoryItem inventoryItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory", id));

        if (inventoryItem.getQuantity() < 10) {
            kafkaTemplate.send("low-stock", inventoryItem.getItemId().toString());
        }


        inventoryMapper.updateEntity(inventoryDto, inventoryItem);
        inventoryItem = inventoryRepository.save(inventoryItem);
        return inventoryMapper.toDto(inventoryItem);

    }


    public void deleteItem(UUID id) {
        log.info("Deleting inventory with id: {}", id);
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
        } else throw new ResourceNotFoundException("Inventory", id);


    }


}
