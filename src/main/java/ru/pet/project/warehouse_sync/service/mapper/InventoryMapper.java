package ru.pet.project.warehouse_sync.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.pet.project.warehouse_sync.db.entity.InventoryItem;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author Gamma on 09.02.2025
 */

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "id", source = "itemId")
    InventoryDto toDto(InventoryItem inventoryItem);

    Collection<InventoryDto> toDtos(Collection<InventoryItem> inventoryItem);

    @Mapping(target = "itemId", source = "id")
    @Mapping(target = "price", expression = "java(formatPrice(inventoryDto.price()))")
    InventoryItem toEntity(InventoryDto inventoryDto);

    @Mapping(target = "itemId", source = "id")
    @Mapping(target = "price", expression = "java(formatPrice(inventoryDto.price()))")
    void updateEntity(InventoryDto inventoryDto, @MappingTarget InventoryItem inventoryItem);

    default BigDecimal formatPrice(BigDecimal price) {
        return price.setScale(2);
    }
}
