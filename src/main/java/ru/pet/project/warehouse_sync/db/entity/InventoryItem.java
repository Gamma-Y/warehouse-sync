package ru.pet.project.warehouse_sync.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Gamma on 06.02.2025
 */

@Getter
@Setter
@Entity
@Table(name = "inventory_item")
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private UUID itemId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        InventoryItem inventoryItem = (InventoryItem) o;
        return quantity == inventoryItem.quantity && Objects.equals(itemId, inventoryItem.itemId) && Objects.equals(name, inventoryItem.name) && Objects.equals(description, inventoryItem.description) && Objects.equals(price, inventoryItem.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(itemId);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(description);
        result = 31 * result + quantity;
        result = 31 * result + Objects.hashCode(price);
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "itemId = " + itemId + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "quantity = " + quantity + ", " +
                "price = " + price + ")";
    }
}
