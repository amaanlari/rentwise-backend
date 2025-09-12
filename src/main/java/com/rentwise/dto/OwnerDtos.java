package com.rentwise.dto;

import lombok.Builder;
import lombok.NonNull;

public interface OwnerDtos {

    @Builder
    record OwnerRequest (
            @NonNull String name,
            @NonNull String email,
            @NonNull String phoneNumber,
            @NonNull String password
    ) {}

    @Builder
    record OwnerResponse (
            @NonNull Long id,
            @NonNull String name,
            @NonNull String email,
            @NonNull String phoneNumber
    ) {}

    @Builder
    record OwnerUpdateRequest (
            @NonNull String name,
            @NonNull String email,
            @NonNull String phoneNumber
    ) {}

    @Builder
    record DeletedOwnerResponse (
            String message,
            OwnerResponse owner
    ) {}
}
