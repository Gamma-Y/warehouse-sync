package ru.pet.project.warehouse_sync.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.pet.project.warehouse_sync.db.entity.InventoryItem;
import ru.pet.project.warehouse_sync.db.repository.InventoryRepository;
import ru.pet.project.warehouse_sync.exeption.ResourceNotFoundException;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;
import ru.pet.project.warehouse_sync.service.mapper.InventoryMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {InventoryService.class, InventoryMapper.class})
class InventoryItemServiceTest {

    @Autowired
    InventoryService inventoryService;
    @Autowired
    InventoryMapper inventoryMapper;

    @MockitoBean
    InventoryRepository inventoryRepository;


    @Test
    void getInventories() {
        List<InventoryItem> inventories = Instancio.ofList(InventoryItem.class).size(5).create();
        when(inventoryRepository.findAll()).thenReturn(inventories);

        Collection<InventoryDto> actual = inventoryService.getInventories();
        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(5, inventories.size());
        });

        verify(inventoryRepository, times(1)).findAll();

    }

    @Test
    void getInventory() {
        var inventory = Instancio.of(InventoryItem.class).create();
        UUID id = inventory.getItemId();

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inventory));
        var result = inventoryService.getInventory(id);

        assertAll(() -> {
            assertNotNull(result);
            assertEquals(id, result.id());
        });

        verify(inventoryRepository, times(1)).findById(id);
    }

    @Test
    void createInventory() {
        var dto = Instancio.of(InventoryDto.class).create();
        InventoryItem inventoryItem = inventoryMapper.toEntity(dto);

        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(inventoryItem);

        var result = inventoryService.createInventory(dto);
        assertAll(() -> {
            assertNotNull(result);
            assertEquals(dto.id(), result.id());
            assertEquals(dto.name(), result.name());
            assertEquals(dto.price(), result.price());
            assertEquals(dto.quantity(), result.quantity());
            assertEquals(dto.description(), result.description());
        });

        verify(inventoryRepository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    void updateInventory() {
        var dto = Instancio.of(InventoryDto.class).create();
        UUID id = dto.id();
        InventoryItem existingInventoryItem = new InventoryItem();
        existingInventoryItem.setItemId(id);


        when(inventoryRepository.findById(id)).thenReturn(Optional.of(existingInventoryItem));
        when(inventoryRepository.save(any(InventoryItem.class))).thenReturn(existingInventoryItem);

        var result = inventoryService.updateInventory(dto);

        assertAll(() -> {
            assertNotNull(result);
            assertEquals(dto.id(), result.id());
            assertEquals(dto.name(), result.name());
            assertEquals(dto.price(), result.price());
            assertEquals(dto.quantity(), result.quantity());
            assertEquals(dto.description(), result.description());
        });

        verify(inventoryRepository, times(1)).findById(id);
        verify(inventoryRepository, times(1)).save(any(InventoryItem.class));
    }

    @Test
    void updateInventoryThrowExceptionWhenInventoryNotFound() {
        var dto = Instancio.of(InventoryDto.class).create();
        UUID id = dto.id();
        InventoryItem existingInventoryItem = new InventoryItem();
        existingInventoryItem.setItemId(id);


        when(inventoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateInventory(dto));

        verify(inventoryRepository, times(1)).findById(id);
        verify(inventoryRepository, times(0)).save(any(InventoryItem.class));
    }

    @Test
    void deleteInventory() {
        UUID id = UUID.randomUUID();
        inventoryService.deleteInventory(id);
        verify(inventoryRepository, times(1)).deleteById(id);
    }
}