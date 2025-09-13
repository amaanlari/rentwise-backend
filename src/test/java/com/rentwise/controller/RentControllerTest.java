package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.RentDtos;
import com.rentwise.dto.RoomDtos;
import com.rentwise.model.enums.RentStatus;
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
class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RentDtos.RentRequest rentRequest;
    private RentDtos.RentUpdateRequest rentUpdateRequest;

    private long ensureRoomAndGetId() throws Exception {
        RoomDtos.RoomRequest roomRequest = RoomDtos.RoomRequest.builder()
                .ownerId(1L)
                .roomNumber("301")
                .roomType("Economy")
                .currentRentAmount(800.0)
                .securityDeposit(1600.0)
                .status(RoomStatus.ACTIVE)
                .notes("For rent test")
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
        rentUpdateRequest = RentDtos.RentUpdateRequest.builder()
                .rentMonth(2)
                .rentYear(2025)
                .amount(900.0)
                .status(RentStatus.PAID)
                .dueDate("2025-02-05")
                .paidDate("2025-02-03")
                .notes("Updated rent")
                .build();
    }

    @Test
    void rent_CRUD_flow() throws Exception {
        long roomId = ensureRoomAndGetId();

        rentRequest = RentDtos.RentRequest.builder()
                .roomId(roomId)
                .rentMonth(1)
                .rentYear(2025)
                .amount(850.0)
                .status(RentStatus.UNPAID)
                .dueDate("2025-01-05")
                .paidDate(null)
                .notes("January rent")
                .build();

        String createResponse = mockMvc.perform(post("/rentwise/api/v1/rent/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long createdId = objectMapper.readTree(createResponse).path("data").path("id").asLong();

        mockMvc.perform(get("/rentwise/api/v1/rent/get/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value((int)createdId))
                .andExpect(jsonPath("$.data.amount").value(850.0));

        mockMvc.perform(put("/rentwise/api/v1/rent/update/" + createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.amount").value(900.0))
                .andExpect(jsonPath("$.data.status").value("PAID"));

        mockMvc.perform(delete("/rentwise/api/v1/rent/delete/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message").value("Rent deleted successfully"));

        mockMvc.perform(get("/rentwise/api/v1/rent/get/" + createdId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.responseStatus").value("FAILURE"));
    }
}
