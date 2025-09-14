package com.rentwise.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rentwise.dto.RentDtos;
import com.rentwise.exception.DuplicateDataException;
import com.rentwise.model.Owner;
import com.rentwise.model.Rent;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.model.enums.RentStatus;
import com.rentwise.model.enums.RoomStatus;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.repository.RentRepository;
import com.rentwise.repository.RoomRepository;
import com.rentwise.repository.TenantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private TenantRepository tenantRepository;

    private Owner createAndSaveOwner() {
        Owner owner = Owner.builder()
                .name("Test Owner")
                .email("owner@example.com")
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

    private Tenant createAndSaveTenant(Room room, int uniqueifier) {
        Tenant tenant = Tenant.builder()
                .room(room)
                .name("Test Tenant " + uniqueifier)
                .contactEmail("tenant" + uniqueifier + "@example.com")
                .contactPhoneNumber("987654321" + uniqueifier)
                .idProofNumber("IDPROOF" + uniqueifier)
                .emergencyContactNumber("012345678" + uniqueifier)
                .leaseStartDate(java.time.Instant.now())
                .leaseEndDate(java.time.Instant.now().plus(java.time.Duration.ofDays(365)))
                .joiningDate(java.time.Instant.now())
                .notes("Some notes for tenant " + uniqueifier)
                .build();
        return tenantRepository.save(tenant);
    }
    
    @Test
    void createRent_shouldSucceed_whenRequestIsValid() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);

        RentDtos.RentRequest rentRequest = RentDtos.RentRequest.builder()
                .roomId(savedRoom.getId())
                .rentMonth(10)
                .rentYear(2023)
                .amount(1500.0)
                .dueDate("2023-10-05")
                .status(RentStatus.UNPAID)
                .build();

        // Act & Assert
        mockMvc.perform(post("/rentwise/api/v1/rent/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.roomId").value(savedRoom.getId()))
                .andExpect(jsonPath("$.data.rentMonth").value(10))
                .andExpect(jsonPath("$.data.rentYear").value(2023))
                .andExpect(jsonPath("$.data.status").value("UNPAID"));
    }

    @Test
    void createRent_shouldFail_whenDuplicateRentExists() throws Exception {
        // Arrange: Create an initial rent entry
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Rent initialRent = Rent.builder()
                .room(savedRoom)
                .rentMonth(11)
                .rentYear(2023)
                .amount(1500.0)
                .status(RentStatus.PAID)
                .dueDate("2023-11-05")
                .build();
        rentRepository.save(initialRent);

        RentDtos.RentRequest duplicateRentRequest = RentDtos.RentRequest.builder()
                .roomId(savedRoom.getId())
                .rentMonth(11) // Same month
                .rentYear(2023) // Same year
                .amount(1600.0)
                .dueDate("2023-11-05")
                .status(RentStatus.UNPAID)
                .build();

        // Act & Assert: Expect a 500-level server error and verify the exception type
        mockMvc.perform(post("/rentwise/api/v1/rent/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRentRequest)))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    assertNotNull(result.getResolvedException());
                    assertInstanceOf(DuplicateDataException.class, result.getResolvedException(),
                            "Expected DataIntegrityViolationException for duplicate entry");
                });
    }

    @Test
    void createRent_shouldFail_whenRoomIsInactive() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room inactiveRoom = Room.builder()
                .owner(savedOwner)
                .roomNumber("202")
                .roomType("Standard")
                .currentRentAmount(1200.0)
                .securityDeposit(2000.0)
                .status(RoomStatus.INACTIVE) // 👈
                .build();
        roomRepository.save(inactiveRoom);

        RentDtos.RentRequest rentRequest = RentDtos.RentRequest.builder()
                .roomId(inactiveRoom.getId())
                .rentMonth(8)
                .rentYear(2024)
                .amount(1200.0)
                .dueDate("2024-08-05")
                .status(RentStatus.UNPAID)
                .build();

        // Act & Assert
        mockMvc.perform(post("/rentwise/api/v1/rent/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentRequest)))
                .andExpect(status().isBadRequest()) // assuming you map IllegalStateException to 400
                .andExpect(jsonPath("$.error.message").value("Cannot create rent for an inactive room"));
    }

    @Test
    void getRent_shouldReturnRent_whenExists() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Rent rent = Rent.builder()
                .room(savedRoom)
                .rentMonth(12)
                .rentYear(2024)
                .amount(2000.0)
                .status(RentStatus.PAID)
                .dueDate("2024-12-05")
                .build();
        Rent savedRent = rentRepository.save(rent);

        // Act & Assert
        mockMvc.perform(get("/rentwise/api/v1/rent/get/" + savedRent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(savedRent.getId()))
                .andExpect(jsonPath("$.data.rentMonth").value(12))
                .andExpect(jsonPath("$.data.rentYear").value(2024));
    }

    @Test
    void getRent_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(get("/rentwise/api/v1/rent/get/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRent_shouldSucceed_whenRequestIsValid() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner); 
        createAndSaveTenant(savedRoom, 1);
        Rent rent = Rent.builder()
                .room(savedRoom)
                .rentMonth(1)
                .rentYear(2025)
                .amount(1000.0)
                .status(RentStatus.UNPAID)
                .dueDate("2025-01-05")
                .build();
        Rent savedRent = rentRepository.save(rent);

        RentDtos.RentUpdateRequest updateRequest = RentDtos.RentUpdateRequest.builder()
                .rentMonth(1)
                .rentYear(2025)
                .dueDate("2025-01-07")
                .amount(1100.0)
                .status(RentStatus.PAID)
                .notes("Payment received")
                .build();

        // Act & Assert
        mockMvc.perform(put("/rentwise/api/v1/rent/update/" + savedRent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(savedRent.getId()))
                .andExpect(jsonPath("$.data.amount").value(1100.0))
                .andExpect(jsonPath("$.data.status").value("PAID"))
                .andExpect(jsonPath("$.data.notes").value("Payment received"));
    }

    @Test
    void updateRent_shouldReturn404_whenNotFound() throws Exception {
        RentDtos.RentUpdateRequest updateRequest = RentDtos.RentUpdateRequest.builder()
                .rentMonth(1)
                .rentYear(2025)
                .amount(100.0)
                .status(RentStatus.PAID)
                .build();
        mockMvc.perform(put("/rentwise/api/v1/rent/update/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRent_shouldSoftDelete_andSubsequentGetReturns404() throws Exception {
        // Arrange
        Owner savedOwner = createAndSaveOwner();
        Room savedRoom = createAndSaveRoom(savedOwner);
        Rent rent = Rent.builder()
                .room(savedRoom)
                .rentMonth(2)
                .rentYear(2025)
                .amount(1500.0)
                .status(RentStatus.PAID)
                .dueDate("2025-02-05")
                .build();
        Rent savedRent = rentRepository.save(rent);

        // Act & Assert: Delete the rent
        mockMvc.perform(delete("/rentwise/api/v1/rent/delete/" + savedRent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.message").value("Rent deleted successfully"))
                .andExpect(jsonPath("$.data.rent.id").value(savedRent.getId()));

        // Act & Assert: Verify it is not accessible anymore
        mockMvc.perform(get("/rentwise/api/v1/rent/get/" + savedRent.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRent_shouldReturn404_whenNotFound() throws Exception {
        mockMvc.perform(delete("/rentwise/api/v1/rent/delete/9999"))
                .andExpect(status().isNotFound());
    }
}
