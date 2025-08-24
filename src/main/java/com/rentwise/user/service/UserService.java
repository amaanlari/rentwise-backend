package com.rentwise.user.service;

import com.rentwise.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service interface for managing user-related operations in the RentWise application.
 * Provides functionality for creating, retrieving, updating, and deleting user records.
 */
public interface UserService {
    UserDto.CreateUserResponseDto createUser(UserDto.CreateUserRequestDto request);
    UserDto.UserResponseDto getUserById(Long id);
    List<UserDto.UserResponseDto> getAllUsers();
    UserDto.UserResponseDto updateUser(Long id, UserDto.UserUpdateDto dto);
    void deleteUser(Long id);
}
