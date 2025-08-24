package com.rentwise.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

public interface UserDto {

    @Builder
    @Value
    @Data
    class CreateUserRequestDto {
        String email;
        String phoneNumber;
        String password;
        String firstName;
        String lastName;
    }

    @Builder
    @Value
    class CreateUserResponseDto {
        Long id;
        String email;
        String phoneNumber;
        String firstName;
        String lastName;
        String slug;
    }

    @Builder
    @Value
    class UserResponseDto {
        private Long id;
        private String email;
        private String phoneNumber;
        private String firstName;
        private String lastName;
        private String slug;
        private String role;
    }

    @Builder
    @Value
    class UserUpdateDto {
        private String email;
        private String phoneNumber;
        private String firstName;
        private String lastName;
        private String role;
    }
}
