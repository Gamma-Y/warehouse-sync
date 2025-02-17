package ru.pet.project.warehouse_sync.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.project.warehouse_sync.db.entity.InventoryItem;

import java.util.UUID;

public interface InventoryRepository extends JpaRepository<InventoryItem, UUID> {
}