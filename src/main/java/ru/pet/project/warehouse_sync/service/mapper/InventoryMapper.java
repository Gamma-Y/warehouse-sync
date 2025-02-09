package ru.pet.project.warehouse_sync.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.pet.project.warehouse_sync.db.entity.Inventory;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;

import java.util.Collection;

/**
 * @author Gamma on 09.02.2025
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "id", source = "itemId")
    InventoryDto toDto(Inventory inventory);

    Collection<InventoryDto> toDtos(Collection<Inventory> inventory);

    @Mapping(target = "itemId", source = "id")
    Inventory toEntity(InventoryDto inventoryDto);

    @Mapping(target = "itemId", source = "id")
    void updateEntity(InventoryDto inventoryDto, @MappingTarget Inventory inventory);


}
