package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.TenantDtos;
import com.rentwise.dto.mapper.TenantMapper;
import com.rentwise.model.Owner;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.model.enums.RoomStatus;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.repository.RoomRepository;
import com.rentwise.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantMapper tenantMapper;

    private Owner createAndSaveOwner() {
        Owner owner = Owner.builder()
                .name("Test Owner")
                .email("test.owner@example.com")
                .phoneNumber("1234567890")
                .password("password")
                .build();
        return ownerRepository.save(owner);
    }

    private Room createAndSaveRoom(Owner owner) {
        Room room = Room.builder()
                .owner(owner)
                .roomNumber("101")
                .roomType("Deluxe")
                .currentRentAmount(1500.0)
                .securityDeposit(3000.0)
                .status(RoomStatus.ACTIVE)
                .build();
        return roomRepository.save(room);
    }

    @Test
    void createTenant_shouldCreateAndReturnTenant() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);

        TenantDtos.TenantRequest tenantRequest = TenantDtos.TenantRequest.builder()
                .roomId(savedRoom.getId())
                .name("John Tenant")
                .contactEmail("john.tenant@example.com")
                .contactPhone("9876543210")
                .idProofNumber("ID12345")
                .emergencyContact("9998887770")
                .leaseStartDate(Instant.parse("2025-01-01T00:00:00Z"))
                .leaseEndDate(Instant.parse("2025-02-01T23:59:59Z"))
                .joiningDate(Instant.parse("2025-01-01T00:00:00Z"))
                .exitDate(Instant.parse("2025-01-31T00:00:00Z"))
                .build();

        // Act & Assert
        mockMvc.perform(post("/rentwise/api/v1/tenant/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.roomId").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.name").value(tenantRequest.name()))
                .andExpect(jsonPath("$.data.contactEmail").value(tenantRequest.contactEmail()))
                .andExpect(jsonPath("$.data.contactPhone").value(tenantRequest.contactPhone()))
                .andExpect(jsonPath("$.data.idProofNumber").value(tenantRequest.idProofNumber()))
                .andExpect(jsonPath("$.data.emergencyContact").value(tenantRequest.emergencyContact()))
                .andExpect(jsonPath("$.data.leaseStartDate").value(tenantRequest.leaseStartDate().toString()))
                .andExpect(jsonPath("$.data.leaseEndDate").value(tenantRequest.leaseEndDate().toString()));
    }

    @Test
    void getTenant_shouldReturnTenant_whenExists() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Tenant tenant = Tenant.builder()
                .room(savedRoom)
                .name("Jane Tenant")
                .contactEmail("jane.tenant@example.com")
                .contactPhone("1122334455")
                .idProofNumber("ID54321")
                .emergencyContact("5556667778")
                .leaseStartDate(Instant.parse("2025-01-01T00:00:00Z"))
                .leaseEndDate(Instant.parse("2025-02-01T23:59:59Z"))
                .joiningDate(Instant.parse("2025-01-01T00:00:00Z"))
                .exitDate(Instant.parse("2025-11-30T00:00:00Z"))
                .build();

        Tenant savedTenant = tenantRepository.save(tenant);

        // Act & Assert
        mockMvc.perform(get("/rentwise/api/v1/tenant/get/" + savedTenant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(savedTenant.getId()))
                .andExpect(jsonPath("$.data.roomId").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.name").value(savedTenant.getName()))
                .andExpect(jsonPath("$.data.contactEmail").value(savedTenant.getContactEmail()))
                .andExpect(jsonPath("$.data.contactPhone").value(savedTenant.getContactPhone()))
                .andExpect(jsonPath("$.data.idProofNumber").value(savedTenant.getIdProofNumber()))
                .andExpect(jsonPath("$.data.emergencyContact").value(savedTenant.getEmergencyContact()))
                .andExpect(jsonPath("$.data.leaseStartDate").value(savedTenant.getLeaseStartDate().toString()))
                .andExpect(jsonPath("$.data.leaseEndDate").value(savedTenant.getLeaseEndDate().toString()))
                .andExpect(jsonPath("$.data.joiningDate").value(savedTenant.getJoiningDate().toString()))
                .andExpect(jsonPath("$.data.exitDate").value(savedTenant.getExitDate().toString()));
    }

    @Test
    void getTenant_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/rentwise/api/v1/tenant/get/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTenant_shouldUpdateAndReturnTenant() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Tenant tenant = Tenant.builder()
                .room(savedRoom)
                .name("Update Me")
                .contactEmail("update.me@example.com")
                .contactPhone("1212121212")
                .idProofNumber("IDToUpdate")
                .emergencyContact("3434343434")
                .leaseStartDate(Instant.parse("2023-01-01T00:00:00Z"))
                .leaseEndDate(Instant.parse("2023-12-31T23:59:59Z"))
                .build();
        Tenant savedTenant = tenantRepository.save(tenant);

        TenantDtos.TenantUpdateRequest updateRequest = TenantDtos.TenantUpdateRequest.builder()
                .name("Updated Name")
                .contactEmail("updated.email@example.com")
                .contactPhone("0000000000")
                .idProofNumber("IDUpdated")
                .emergencyContact("1111111111")
                .leaseStartDate(Instant.parse("2025-01-01T00:00:00Z"))
                .leaseEndDate(Instant.parse("2025-01-31T23:59:59Z"))
                .joiningDate(Instant.parse("2025-01-01T00:00:00Z"))
                .exitDate(Instant.parse("2025-12-31T00:00:00Z"))
                .build();

        // Act & Assert
        mockMvc.perform(put("/rentwise/api/v1/tenant/update/" + savedTenant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(savedTenant.getId()))
                .andExpect(jsonPath("$.data.roomId").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.name").value(updateRequest.name()))
                .andExpect(jsonPath("$.data.contactEmail").value(updateRequest.contactEmail()))
                .andExpect(jsonPath("$.data.contactPhone").value(updateRequest.contactPhone()))
                .andExpect(jsonPath("$.data.idProofNumber").value(updateRequest.idProofNumber()))
                .andExpect(jsonPath("$.data.emergencyContact").value(updateRequest.emergencyContact()))
                .andExpect(jsonPath("$.data.leaseStartDate").value(updateRequest.leaseStartDate().toString()))
                .andExpect(jsonPath("$.data.leaseEndDate").value(updateRequest.leaseEndDate().toString()))
                .andExpect(jsonPath("$.data.joiningDate").value(updateRequest.joiningDate().toString()))
                .andExpect(jsonPath("$.data.exitDate").value(updateRequest.exitDate().toString()));
    }

    @Test
    void deleteTenant_shouldSoftDeleteTenant_andSubsequentGetReturns404() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Tenant tenant = Tenant.builder()
                .room(savedRoom)
                .name("Delete Me")
                .contactEmail("delete.me@example.com")
                .contactPhone("5656565656")
                .idProofNumber("IDToDelete")
                .emergencyContact("9898989898")
                .leaseStartDate(Instant.parse("2022-01-01T00:00:00Z"))
                .leaseEndDate(Instant.parse("2022-12-31T23:59:59Z"))
                .joiningDate(Instant.parse("2025-01-01T00:00:00Z"))
                .exitDate(Instant.parse("2025-12-31T00:00:00Z"))
                .build();
        Tenant savedTenant = tenantRepository.save(tenant);
        // Act & Assert: Delete the tenant
        mockMvc.perform(delete("/rentwise/api/v1/tenant/delete/" + savedTenant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message").value("Tenant deleted successfully"))
                .andExpect(jsonPath("$.data.tenant.id").value(tenant.getId()));

        // Act & Assert: Verify it is not accessible anymore
        mockMvc.perform(get("/rentwise/api/v1/tenant/get/" + savedTenant.getId()))
                .andExpect(status().isNotFound());
    }
}
