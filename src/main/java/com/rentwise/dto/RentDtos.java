package com.rentwise.dto;

import com.rentwise.model.enums.RentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

public interface RentDtos {

    @Builder
    record RentRequest(
            @NotNull(message = "Room ID is required")
            Long roomId,

            @NotNull(message = "Rent month is required")
            @Range(min = 1, max = 12, message = "Rent month must be between 1 and 12")
            Integer rentMonth,

            @NotNull(message = "Rent year is required")
            @Positive(message = "Rent year must be positive")
            Integer rentYear,

            @NotNull(message = "Amount is required")
            @Positive(message = "Amount must be positive")
            Double amount,

            @NotNull(message = "Status is required")
            RentStatus status,

            @NotNull(message = "Due date is required")
            String dueDate,

            String paidDate,
            String notes
    ) {}

    @Builder
    record RentUpdateRequest(
            @NotNull(message = "Rent month must be positive")
            @Range(min = 1, max = 12, message = "Rent month must be between 1 and 12")
            Integer rentMonth,

            @NotNull(message = "Rent year must be positive")
            @Positive(message = "Rent year must be positive")
            Integer rentYear,

            @NotNull(message = "Amount is required")
            @Positive(message = "Amount must be positive")
            Double amount,

            @NotNull(message = "Status is required")
            RentStatus status,

            String dueDate,
            String paidDate,
            String notes
    ) {}

    @Builder
    record RentResponse(
            @NonNull Long id,
            @NonNull Long roomId,
            @NonNull Integer rentMonth,
            @NonNull Integer rentYear,
            @NonNull Double amount,
            @NonNull RentStatus status,
            String dueDate,
            String paidDate,
            String notes
    ) {}

    @Builder
    record DeletedRentResponse(
            String message,
            RentResponse rent
    ) {}
}
