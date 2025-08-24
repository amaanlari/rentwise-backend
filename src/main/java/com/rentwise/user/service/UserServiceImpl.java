package com.rentwise.user.service;

import com.rentwise.user.dto.UserDto;
import com.rentwise.user.model.Role;
import com.rentwise.user.model.User;
import com.rentwise.user.repository.UserRepository;
import com.rentwise.util.user.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtil userUtil;

    @Override
    public UserDto.CreateUserResponseDto createUser(UserDto.CreateUserRequestDto request) {
        User newUser = User.builder()
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword()) // In a real application, ensure to hash the password
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .slug(userUtil.generateSlug(request.getFirstName(), request.getLastName()))
                .role(Role.PROPERTY_OWNER) // Default role
                .build();

        User savedUser = userRepository.save(newUser);
        return UserDto.CreateUserResponseDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .slug(savedUser.getSlug())
                .build();
    }

    @Override
    public UserDto.UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserDto.UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .slug(user.getSlug())
                .role(user.getRole().name())
                .build();
    }

    @Override
    public List<UserDto.UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserDto.UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .slug(user.getSlug())
                .role(user.getRole().name())
                .build()).toList();
    }

    @Override
    public UserDto.UserResponseDto updateUser(Long id, UserDto.UserUpdateDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getRole() != null) user.setRole(Role.valueOf(dto.getRole()));

        User updatedUser = userRepository.save(user);
        return UserDto.UserResponseDto.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .phoneNumber(updatedUser.getPhoneNumber())
                .firstName(updatedUser.getFirstName())
                .lastName(updatedUser.getLastName())
                .slug(updatedUser.getSlug())
                .role(updatedUser.getRole().name())
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);

    }
}
