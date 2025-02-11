package ru.pet.project.warehouse_sync.configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Gamma on 11.02.2025
 */
public class TestUser {
    private TestUser() {
    }

    public static UserDetails createTestUser() {
        return User.withUsername("testuser")
                .password("password")
                .roles("USER")
                .build();
    }
}
