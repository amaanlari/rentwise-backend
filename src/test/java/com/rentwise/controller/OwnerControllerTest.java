package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.OwnerDtos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private OwnerDtos.OwnerRequest ownerRequest;
    private OwnerDtos.OwnerRequest ownerRequestWithExistingEmail;
    private OwnerDtos.OwnerRequest ownerRequestWithExistingPhoneNumber;
    private OwnerDtos.OwnerUpdateRequest ownerUpdateRequest;

    @BeforeEach
    void setUp() {
        ownerRequest = new OwnerDtos.OwnerRequest("John Doe", "john.doe@example.com", "1234567890", "password");
        ownerRequestWithExistingEmail = new OwnerDtos.OwnerRequest("Alice Johnson", "alice@example.com", "1111112222", "passAlice");
        ownerRequestWithExistingPhoneNumber = new OwnerDtos.OwnerRequest("Alice Johnson", "alice5@example.com", "1111111111", "passAlice");
        ownerUpdateRequest = new OwnerDtos.OwnerUpdateRequest("John Doe Updated", "john.doe.updated@example.com", "0987654321");

    }

    @Test
    void createOwner_shouldCreateAndReturnOwner() throws Exception {
        mockMvc.perform(post("/rentwise/api/v1/owner/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("1234567890"));
    }

    @Test
    void createOwner_shouldReturn409_whenEmailAlreadyExists() throws Exception {
        mockMvc.perform(post("/rentwise/api/v1/owner/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestWithExistingEmail)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.responseStatus").value("FAILURE"))
                .andExpect(jsonPath("$.error.status").value("CONFLICT"))
                .andExpect(jsonPath("$.error.message").value("User with email already exists"));
    }

    @Test
    void createOwner_shouldReturn409_whenPhoneNumberAlreadyExists() throws Exception {
        mockMvc.perform(post("/rentwise/api/v1/owner/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequestWithExistingPhoneNumber)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.responseStatus").value("FAILURE"))
                .andExpect(jsonPath("$.error.status").value("CONFLICT"))
                .andExpect(jsonPath("$.error.message").value("User with phone number already exists"));
    }

    @Test
    void getOwnerById_shouldReturnOwner_whenExists() throws Exception {
        // Using seeded data from data.sql (ID = 1)
        mockMvc.perform(get("/rentwise/api/v1/owner/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Alice Johnson"))
                .andExpect(jsonPath("$.data.email").value("alice@example.com"));
    }

    @Test
    void getOwnerById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/rentwise/api/v1/owner/get/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseStatus").value("FAILURE"))
                .andExpect(jsonPath("$.error.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("Owner not found"));
    }

    @Test
    void updateOwner_shouldUpdateAndReturnOwner() throws Exception {
        // Update seeded owner with ID = 2
        mockMvc.perform(put("/rentwise/api/v1/owner/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.name").value("John Doe Updated"))
                .andExpect(jsonPath("$.data.email").value("john.doe.updated@example.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("0987654321"));
    }

    @Test
    void deleteOwner_shouldSoftDeleteOwner_andSubsequentGetReturns404() throws Exception {
        // Delete seeded owner with ID = 3
        mockMvc.perform(delete("/rentwise/api/v1/owner/delete/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.message").value("Owner deleted successfully"))
                .andExpect(jsonPath("$.data.owner.id").value(3))
                .andExpect(jsonPath("$.data.owner.email").value("charlie@example.com"));

        // Verify it is not accessible anymore
        mockMvc.perform(get("/rentwise/api/v1/owner/get/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.status").value("NOT_FOUND"));
    }
}
