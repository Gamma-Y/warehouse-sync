package ru.pet.project.warehouse_sync.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.pet.project.warehouse_sync.configuration.PostgresContainerConfig;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.flyway.locations=classpath:db/migration,classpath:db/testdata"
})
class InventoryControllerIT extends PostgresContainerConfig {
    @Autowired
    MockMvc mockMvc;


    @Test
    void testGetAllInventory_403() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventories"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser
    void testGetAllInventory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/inventories"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }
}