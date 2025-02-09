package ru.pet.project.warehouse_sync.service.mapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.pet.project.warehouse_sync.db.entity.Inventory;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryMapperTest {

    private final InventoryMapper inventoryMapper = Mappers.getMapper(InventoryMapper.class);

    @Test
    void toDto() {
        Inventory inventory = Instancio.of(Inventory.class).create();
        InventoryDto dto = inventoryMapper.toDto(inventory);

        assertAll(() -> {
            assertNotNull(dto);
            assertEquals(inventory.getItemId(), dto.id());
            assertEquals(inventory.getQuantity(), dto.quantity());
            assertEquals(inventory.getPrice(), dto.price());
            assertEquals(inventory.getDescription(), dto.description());
        });
    }

    @Test
    void toDtos() {
        List<Inventory> inventories = Instancio.ofList(Inventory.class).size(5).create();
        List<InventoryDto> dtos = new ArrayList<>(inventoryMapper.toDtos(inventories));


        assertAll(() -> {
            assertNotNull(dtos);
            assertEquals(inventories.size(), dtos.size());

            for (int i = 0; i < inventories.size(); i++) {
                assertEquals(inventories.get(i).getItemId(), dtos.get(i).id());
                assertEquals(inventories.get(i).getQuantity(), dtos.get(i).quantity());
                assertEquals(inventories.get(i).getPrice(), dtos.get(i).price());
                assertEquals(inventories.get(i).getDescription(), dtos.get(i).description());
            }
        });
    }

    @Test
    void toEntity() {
        InventoryDto dto = Instancio.of(InventoryDto.class).create();
        Inventory entity = inventoryMapper.toEntity(dto);

        assertAll(() -> {
            assertNotNull(entity);
            assertEquals(dto.id(), entity.getItemId());
            assertEquals(dto.quantity(), entity.getQuantity());
            assertEquals(dto.price(), entity.getPrice());
            assertEquals(dto.description(), entity.getDescription());
        });
    }

    @Test
    void updateEntity() {
        Inventory inventory = Instancio.of(Inventory.class).create();
        InventoryDto dto = Instancio.of(InventoryDto.class).create();

        inventoryMapper.updateEntity(dto, inventory);


        assertAll(() -> {
            assertEquals(dto.id(), inventory.getItemId());
            assertEquals(dto.quantity(), inventory.getQuantity());
            assertEquals(dto.price(), inventory.getPrice());
            assertEquals(dto.description(), inventory.getDescription());
        });
    }

    @Test
    void formatPrice() {
        BigDecimal price = new BigDecimal("1500");

        BigDecimal formattedPrice = inventoryMapper.formatPrice(price);

        assertEquals(new BigDecimal("1500.00"), formattedPrice);
    }
}