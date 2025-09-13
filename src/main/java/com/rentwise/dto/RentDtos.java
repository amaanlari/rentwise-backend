package com.rentwise.dto;

import com.rentwise.model.enums.RentStatus;
import lombok.Builder;
import lombok.NonNull;

public interface RentDtos {

    @Builder
    record RentRequest(
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
    record RentUpdateRequest(
            @NonNull Integer rentMonth,
            @NonNull Integer rentYear,
            @NonNull Double amount,
            @NonNull RentStatus status,
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
