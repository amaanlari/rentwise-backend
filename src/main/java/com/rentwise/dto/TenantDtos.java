package com.rentwise.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

import java.time.Instant;

public interface TenantDtos {

    @Builder
    record TenantRequest(
            @NotNull(message = "Room ID is required")
            Long roomId,

            @NotNull(message = "Name is required")
            String name,

            @NotNull(message = "Contact email is required")
            @Email(message = "Invalid email format")
            String contactEmail,

            @NotNull(message = "Contact phone number is required")
            String contactPhoneNumber,

            @NotNull(message = "ID proof number is required")
            String idProofNumber,

            @NotNull(message = "Emergency contact number is required")
            String emergencyContactNumber,

            @NotNull(message = "Lease start date")
            Instant leaseStartDate,

            @NotNull(message = "Lease end date")
            Instant leaseEndDate,

            Instant joiningDate,
            Instant exitDate,
            String notes
    ) {}

    @Builder
    record TenantUpdateRequest(
            @NotNull(message = "Name is required")
            String name,

            @NotNull(message = "Contact email is required")
            @Email(message = "Invalid email format")
            String contactEmail,

            @NotNull(message = "Contact phone number is required")
            String contactPhoneNumber,

            @NotNull(message = "ID proof number is required")
            String idProofNumber,

            @NotNull(message = "Emergency contact number is required")
            String emergencyContactNumber,

            @NotNull(message = "Lease start date")
            Instant leaseStartDate,

            @NotNull(message = "Lease end date")
            Instant leaseEndDate,

            Instant joiningDate,
            Instant exitDate,
            String notes
    ) {}

    @Builder
    record TenantResponse(
            @NonNull Long id,
            @NonNull Long roomId,
            @NonNull String name,
            @NonNull String contactEmail,
            @NonNull String contactPhoneNumber,
            @NonNull String idProofNumber,
            @NonNull String emergencyContactNumber,
            Instant joiningDate,
            Instant exitDate,
            @NonNull Instant leaseStartDate,
            @NonNull Instant leaseEndDate,
            String notes
    ) {}

    @Builder
    record DeletedTenantResponse(
            String message,
            TenantResponse tenant
    ) {}
}
