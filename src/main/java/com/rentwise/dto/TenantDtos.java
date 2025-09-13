package com.rentwise.dto;

import lombok.Builder;
import lombok.NonNull;

public interface TenantDtos {

    @Builder
    record TenantRequest(
            @NonNull Long roomId,
            @NonNull String name,
            @NonNull String contactEmail,
            @NonNull String contactPhone,
            String idProofNumber,
            String emergencyContact,
            String joiningDate,
            String exitDate,
            String leaseStartDate,
            String leaseEndDate,
            String notes
    ) {}

    @Builder
    record TenantUpdateRequest(
            @NonNull String name,
            @NonNull String contactEmail,
            @NonNull String contactPhone,
            String idProofNumber,
            String emergencyContact,
            String joiningDate,
            String exitDate,
            String leaseStartDate,
            String leaseEndDate,
            String notes
    ) {}

    @Builder
    record TenantResponse(
            @NonNull Long id,
            @NonNull Long roomId,
            @NonNull String name,
            @NonNull String contactEmail,
            @NonNull String contactPhone,
            String idProofNumber,
            String emergencyContact,
            String joiningDate,
            String exitDate,
            String leaseStartDate,
            String leaseEndDate,
            String notes
    ) {}

    @Builder
    record DeletedTenantResponse(
            String message,
            TenantResponse tenant
    ) {}
}
