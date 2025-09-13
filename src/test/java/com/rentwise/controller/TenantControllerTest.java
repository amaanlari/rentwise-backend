package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.RoomDtos;
import com.rentwise.dto.TenantDtos;
import com.rentwise.model.enums.RoomStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TenantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TenantDtos.TenantRequest tenantRequest;
    private TenantDtos.TenantUpdateRequest tenantUpdateRequest;

    private long ensureRoomAndGetId() throws Exception {
        RoomDtos.RoomRequest roomRequest = RoomDtos.RoomRequest.builder()
                .ownerId(1L)
                .roomNumber("201")
                .roomType("Standard")
                .currentRentAmount(1000.0)
                .securityDeposit(2000.0)
                .status(RoomStatus.ACTIVE)
                .notes("For tenant test")
                .build();
        String response = mockMvc.perform(post("/rentwise/api/v1/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readTree(response).path("data").path("id").asLong();
    }

    @BeforeEach
    void setUp() {
        tenantUpdateRequest = TenantDtos.TenantUpdateRequest.builder()
                .name("Jane Doe Updated")
                .contactEmail("jane.updated@example.com")
                .contactPhone("2223334444")
                .idProofNumber("ID5678")
                .emergencyContact("Mary 555-9999")
                .joiningDate("2024-02-01")
                .exitDate("2025-02-01")
                .leaseStartDate("2024-02-01")
                .leaseEndDate("2025-02-01")
                .notes("Updated tenant")
                .build();
    }

    @Test
    void tenant_CRUD_flow() throws Exception {
        long roomId = ensureRoomAndGetId();

        tenantRequest = TenantDtos.TenantRequest.builder()
                .roomId(roomId)
                .name("Jane Doe")
                .contactEmail("jane@example.com")
                .contactPhone("1112223333")
                .idProofNumber("ID1234")
                .emergencyContact("John 555-1234")
                .joiningDate("2024-01-01")
                .exitDate("2025-01-01")
                .leaseStartDate("2024-01-01")
                .leaseEndDate("2024-12-31")
                .notes("Good tenant")
                .build();

        String createResponse = mockMvc.perform(post("/rentwise/api/v1/tenant/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenantRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long createdId = objectMapper.readTree(createResponse).path("data").path("id").asLong();

        mockMvc.perform(get("/rentwise/api/v1/tenant/get/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value((int)createdId))
                .andExpect(jsonPath("$.data.name").value("Jane Doe"));

        mockMvc.perform(put("/rentwise/api/v1/tenant/update/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenantUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Jane Doe Updated"))
                .andExpect(jsonPath("$.data.contactEmail").value("jane.updated@example.com"));

        mockMvc.perform(delete("/rentwise/api/v1/tenant/delete/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message").value("Tenant deleted successfully"));

        mockMvc.perform(get("/rentwise/api/v1/tenant/get/" + createdId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseStatus").value("FAILURE"));
    }
}
