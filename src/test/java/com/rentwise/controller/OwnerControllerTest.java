package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.OwnerDtos;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import jakarta.transaction.Transactional;
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
@Transactional
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void createOwner_shouldCreateAndReturnOwner() throws Exception {
        OwnerDtos.OwnerRequest ownerRequest = new OwnerDtos.OwnerRequest("John Doe", "john.doe@example.com", "1234567890", "password");

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
        // Arrange: Save an owner with a specific email first
        ownerRepository.save(Owner.builder().name("Alice Johnson").email("alice@example.com").phoneNumber("1111112222").password("pass").build());

        // Act & Assert: Attempt to create a new owner with the same email
        OwnerDtos.OwnerRequest ownerRequestWithExistingEmail = new OwnerDtos.OwnerRequest("Another Name", "alice@example.com", "3334445555", "password123");
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
        // Arrange: Save an owner with a specific phone number first
        ownerRepository.save(Owner.builder().name("Bob Smith").email("bob@example.com").phoneNumber("1111111111").password("pass").build());

        // Act & Assert: Attempt to create a new owner with the same phone number
        OwnerDtos.OwnerRequest ownerRequestWithExistingPhoneNumber = new OwnerDtos.OwnerRequest("Another Name", "another@example.com", "1111111111", "password123");
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
        // Arrange
        Owner savedOwner = ownerRepository.save(Owner.builder().name("Alice Johnson").email("alice@example.com").phoneNumber("1111111111").password("pass").build());

        // Act & Assert
        mockMvc.perform(get("/rentwise/api/v1/owner/get/" + savedOwner.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(savedOwner.getId()))
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
        // Arrange
        Owner savedOwner = ownerRepository.save(Owner.builder().name("Bob Smith").email("bob@example.com").phoneNumber("2222222222").password("pass").build());
        OwnerDtos.OwnerUpdateRequest ownerUpdateRequest = new OwnerDtos.OwnerUpdateRequest("Bob Smith Updated", "bob.updated@example.com", "0987654321");

        // Act & Assert
        mockMvc.perform(put("/rentwise/api/v1/owner/update/" + savedOwner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(savedOwner.getId()))
                .andExpect(jsonPath("$.data.name").value("Bob Smith Updated"))
                .andExpect(jsonPath("$.data.email").value("bob.updated@example.com"))
                .andExpect(jsonPath("$.data.phoneNumber").value("0987654321"));
    }

    @Test
    void deleteOwner_shouldSoftDeleteOwner_andSubsequentGetReturns404() throws Exception {
        // Arrange
        Owner savedOwner = ownerRepository.save(Owner.builder().name("Charlie Brown").email("charlie@example.com").phoneNumber("3333333333").password("pass").build());

        // Act & Assert: Delete the owner
        mockMvc.perform(delete("/rentwise/api/v1/owner/delete/" + savedOwner.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.message").value("Owner deleted successfully"))
                .andExpect(jsonPath("$.data.owner.id").value(savedOwner.getId()))
                .andExpect(jsonPath("$.data.owner.email").value("charlie@example.com"));

        // Act & Assert: Verify it is not accessible anymore
        mockMvc.perform(get("/rentwise/api/v1/owner/get/" + savedOwner.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.status").value("NOT_FOUND"));
    }
}
