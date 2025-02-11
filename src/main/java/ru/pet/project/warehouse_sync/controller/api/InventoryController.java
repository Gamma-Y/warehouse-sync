package ru.pet.project.warehouse_sync.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pet.project.warehouse_sync.service.InventoryService;
import ru.pet.project.warehouse_sync.service.dto.InventoryDto;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Gamma on 09.02.2025
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventories")
@Tag(name = "API для управления предметами", description = "API для работы с элементами склада")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @Operation(
            summary = "Получить все запасы",
            description = "Возвращает список всех запасов, доступных в системе."
    )
    @ApiResponse(responseCode = "200", description = "Список запасов успешно получен")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    public ResponseEntity<Collection<InventoryDto>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getInventories());
    }

    @Operation(
            summary = "Получить предмет по UUID",
            description = "Возвращает один предмет по его уникальному идентификатору (UUID)."
    )
    @ApiResponse(responseCode = "200", description = "Предмет найден")
    @ApiResponse(responseCode = "404", description = "Предмет не найден")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryDto> getInventoryById(@PathVariable UUID id) {
        return ResponseEntity.ok(inventoryService.getInventory(id));
    }

    @Operation(
            summary = "Создать предмет",
            description = "Создает новый предмет с предоставленными данными."
    )
    @ApiResponse(responseCode = "200", description = "Предмет успешно создан")
    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody @Valid InventoryDto inventoryDto) {
        return ResponseEntity.ok(inventoryService.createInventory(inventoryDto));
    }

    @Operation(
            summary = "Обновить предмета",
            description = "Обновляет существующего предмета с предоставленными данными."
    )
    @ApiResponse(responseCode = "200", description = "Предмет успешно обновлен")
    @ApiResponse(responseCode = "400", description = "Некорректные входные данные")
    @ApiResponse(responseCode = "404", description = "Предмет не найден")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    @PutMapping
    public ResponseEntity<InventoryDto> updateInventory(@RequestBody @Valid InventoryDto inventoryDto) {
        return ResponseEntity.ok(inventoryService.updateInventory(inventoryDto));
    }

    @Operation(
            summary = "Удалить предмет по UUID",
            description = "Удаляет предмет по его уникальному идентификатору (UUID)."
    )
    @ApiResponse(responseCode = "204", description = "Предмет успешно удален")
    @ApiResponse(responseCode = "404", description = "Предмет не найден")
    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable UUID id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
