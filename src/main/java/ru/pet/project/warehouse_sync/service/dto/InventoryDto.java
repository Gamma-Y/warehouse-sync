package ru.pet.project.warehouse_sync.service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * @author Gamma on 06.02.2025
 */

@Data
public class InventoryDto {

    @NotNull
    @Length(max = 100)
    private String name;

    @Length(max = 255)
    private String description;

    @PositiveOrZero(message = "Quantity cannot be lower than 0")
    private int quantity;


    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 fractional digits")
    private BigDecimal price;


}
