package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.RoomDtos;
import com.rentwise.model.Owner;
import com.rentwise.model.Room;
import com.rentwise.model.enums.ResponseStatus;
import com.rentwise.model.enums.RoomStatus;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.repository.RoomRepository;
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
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Owner createAndSaveOwner() {
        Owner owner = Owner.builder()
                .name("Test Owner")
                .email("test.owner@example.com")
                .phoneNumber("1234567890")
                .password("password")
                .build();
        return ownerRepository.save(owner);
    }

    @Test
    void createRoom_shouldCreateAndReturnRoom() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        RoomDtos.RoomRequest roomRequest = RoomDtos.RoomRequest.builder()
                .ownerId(savedOwner.getId())
                .roomNumber("101")
                .roomType("Deluxe")
                .currentRentAmount(1500.0)
                .securityDeposit(3000.0)
                .status(RoomStatus.ACTIVE)
                .notes("Near entrance")
                .build();

        // Act & Assert
        mockMvc.perform(post("/rentwise/api/v1/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseStatus").value(ResponseStatus.SUCCESS.name()))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.ownerId").value(roomRequest.ownerId()))
                .andExpect(jsonPath("$.data.roomNumber").value(roomRequest.roomNumber()))
                .andExpect(jsonPath("$.data.roomType").value(roomRequest.roomType()))
                .andExpect(jsonPath("$.data.currentRentAmount").value(roomRequest.currentRentAmount()))
                .andExpect(jsonPath("$.data.securityDeposit").value(roomRequest.securityDeposit()))
                .andExpect(jsonPath("$.data.status").value(roomRequest.status().name()));
    }

    @Test
    void createRoom_shouldReturn400_whenOwnerIdIsNull() throws Exception {
        // Arrange
        RoomDtos.RoomRequest invalidRequest = RoomDtos.RoomRequest.builder()
                .ownerId(null)
                .roomNumber("101")
                .roomType("Deluxe")
                .currentRentAmount(1500.0)
                .securityDeposit(3000.0)
                .status(RoomStatus.ACTIVE)
                .notes("Test room")
                .build();

        // Act & Assert
        mockMvc.perform(post("/rentwise/api/v1/room/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.subErrors[0]").value("ownerId: Owner ID is required"));
    }

    @Test
    void getRoomById_shouldReturnRoom_whenExists() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room room = Room.builder()
                .owner(savedOwner)
                .roomNumber("102")
                .roomType("Standard")
                .currentRentAmount(1200.0)
                .securityDeposit(2400.0)
                .status(RoomStatus.ACTIVE)
                .build();
        Room savedRoom = roomRepository.save(room);

        // Act & Assert
        mockMvc.perform(get("/rentwise/api/v1/room/get/" + savedRoom.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.roomNumber").value("102"));
    }

    @Test
    void getRoomById_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/rentwise/api/v1/room/get/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRoom_shouldUpdateAndReturnRoom() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room room = Room.builder()
                .owner(savedOwner)
                .roomNumber("201")
                .roomType("Suite")
                .currentRentAmount(2500.0)
                .securityDeposit(5000.0)
                .status(RoomStatus.INACTIVE)
                .build();
        Room savedRoom = roomRepository.save(room);

        RoomDtos.RoomUpdateRequest roomUpdateRequest = RoomDtos.RoomUpdateRequest.builder()
                .roomNumber("201-Updated")
                .roomType("Premium Suite")
                .currentRentAmount(2700.0)
                .securityDeposit(5400.0)
                .status(RoomStatus.ACTIVE)
                .notes("Recently updated")
                .build();

        // Act & Assert
        mockMvc.perform(put("/rentwise/api/v1/room/update/" + savedRoom.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.id").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.roomNumber").value("201-Updated"))
                .andExpect(jsonPath("$.data.roomType").value("Premium Suite"))
                .andExpect(jsonPath("$.data.currentRentAmount").value(2700.0));
    }

    @Test
    void deleteRoom_shouldSoftDeleteRoom_andSubsequentGetReturns404() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room room = Room.builder()
                .owner(savedOwner)
                .roomNumber("301")
                .roomType("Standard")
                .currentRentAmount(1000.0)
                .securityDeposit(2000.0)
                .status(RoomStatus.ACTIVE)
                .build();
        Room savedRoom = roomRepository.save(room);

        // Act & Assert: Delete the room
        mockMvc.perform(delete("/rentwise/api/v1/room/delete/" + savedRoom.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.responseStatus").value("SUCCESS"))
                .andExpect(jsonPath("$.data.message").value("Room deleted successfully"))
                .andExpect(jsonPath("$.data.room.id").value(savedRoom.getId()));

        // Act & Assert: Verify it is not accessible anymore
        mockMvc.perform(get("/rentwise/api/v1/room/get/" + savedRoom.getId()))
                .andExpect(status().isNotFound());
    }
}
