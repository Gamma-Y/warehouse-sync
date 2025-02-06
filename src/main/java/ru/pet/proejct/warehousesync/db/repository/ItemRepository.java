package ru.pet.proejct.warehousesync.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pet.proejct.warehousesync.db.entity.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}