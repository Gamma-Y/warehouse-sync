package ru.pet.project.warehouse_sync.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * @author Gamma on 06.02.2025
 */

public record InventoryDto(
        @NotNull
        @Length(max = 100)
        String name,

        @Length(max = 255)
        String description,

        @PositiveOrZero(message = "Quantity cannot be lower than 0")
        int quantity,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(value = "0.0", message = "Price must be greater than 0")
        @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 fractional digits")
        BigDecimal price
) {
}
