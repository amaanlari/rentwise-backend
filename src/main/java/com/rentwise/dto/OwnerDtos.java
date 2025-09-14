package com.rentwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;

public interface OwnerDtos {

    @Builder
    record OwnerRequest (
            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            String email,

            @NotBlank(message = "Phone number is required")
            String phoneNumber,

            @NotBlank(message = "Password is required")
            String password

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
            @NotBlank(message = "Name is required")
            String name,

            @NotBlank(message = "Email is required")
            String email,

            @NotBlank(message = "Phone number is required")
            String phoneNumber
    ) {}

    @Builder
    record DeletedOwnerResponse (
            String message,
            OwnerResponse owner
    ) {}
}
