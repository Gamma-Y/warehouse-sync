package ru.pet.project.warehouse_sync.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Gamma on 06.02.2025
 */

@Schema(description = "DTO для предметов складской системы")
public record InventoryDto(

        @Schema(description = "Уникальный индефикатор предмета", nullable = true)
        UUID id,

        @NotNull
        @Length(max = 100)
        @Schema(description = "Наименование предмета", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Length(max = 255)
        @Schema(description = "Описание предмета", nullable = true, maxLength = 255)
        String description,

        @PositiveOrZero(message = "Количество предметов не может меньше 0")
        @Schema(description = "Количество предметов", requiredMode = Schema.RequiredMode.REQUIRED)
        int quantity,

        @Schema(description = "Цена предмета", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Цена не может отсутствовать")
        @DecimalMin(value = "0.0", message = "Цена должна быть больше 0")
        @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 fractional digits")
        BigDecimal price
) {

}
