package com.rentwise.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

public interface OwnerDtos {

    @Data
    @Builder
    @Jacksonized
    class OwnerRequest {
        private String name;
        private String email;
        private String phoneNumber;
        private String password;
    }

    @Data
    @Builder
    @Jacksonized
    class OwnerResponse {
        private Long id;
        private String name;
        private String email;
        private String phoneNumber;
    }

    @Data
    @Builder
    @Jacksonized
    class OwnerUpdateRequest {
        private String name;
        private String email;
        private String phoneNumber;
    }

    @Data
    @Builder
    @Jacksonized
    class DeletedOwnerResponse {
        private String message;
        private OwnerResponse owner;
    }
}
