package ru.pet.project.warehouse_sync.configuration.security;

import jakarta.validation.constraints.NotNull;

/**
 * @author Gamma on 10.02.2025
 */
public record AuthRequest(@NotNull String username, @NotNull String password) {
}
