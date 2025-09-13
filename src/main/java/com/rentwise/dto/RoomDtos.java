package com.rentwise.dto;

import com.rentwise.model.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public interface RoomDtos {

    @Builder
    record RoomRequest(
            @NotNull(message = "Owner ID is required")
            Long ownerId,
            @NotBlank(message = "Room number is required")
            String roomNumber,
            @NotBlank(message = "Room type is required")
            String roomType,
            @NotNull(message = "Current rent amount is required")
            @Positive(message = "Current rent amount must be positive")
            Double currentRentAmount,
            @NotNull(message = "Security deposit is required")
            @Positive(message = "Security deposit must be positive")
            Double securityDeposit,
            @NotNull(message = "Room status is required")
            RoomStatus status,
            String notes
    ) {}

    @Builder
    record RoomUpdateRequest(
            @NotBlank(message = "Room number is required")
            String roomNumber,
            @NotBlank(message = "Room type is required")
            String roomType,
            @NotNull(message = "Current rent amount is required")
            @Positive(message = "Current rent amount must be positive")
            Double currentRentAmount,
            @NotNull(message = "Security deposit is required")
            @Positive(message = "Security deposit must be positive")
            Double securityDeposit,
            @NotNull(message = "Room status is required")
            RoomStatus status,
            String notes
    ) {}

    @Builder
    record RoomResponse(
            @NotNull Long id,
            @NotNull Long ownerId,
            @NotNull String roomNumber,
            @NotNull String roomType,
            @NotNull Double currentRentAmount,
            @NotNull Double securityDeposit,
            @NotNull RoomStatus status,
            String notes
    ) {}

    @Builder
    record DeletedRoomResponse(
            String message,
            RoomResponse room
    ) {}
}