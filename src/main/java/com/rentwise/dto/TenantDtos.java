package com.rentwise.dto;

import lombok.Builder;
import lombok.NonNull;

import java.time.Instant;

public interface TenantDtos {

    @Builder
    record TenantRequest(
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
    record TenantUpdateRequest(
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
