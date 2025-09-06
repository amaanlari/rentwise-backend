package com.rentwise.mapper;

import com.rentwise.dto.OwnerDto;
import com.rentwise.model.Owner;

public class OwnerMapper {

    public static OwnerDto toDto(Owner owner) {
        return OwnerDto.builder()
                .ownerId(owner.getOwnerId())
                .name(owner.getName())
                .contact(owner.getContact())
                .email(owner.getEmail())
                .build();
    }

    public static Owner toEntity(OwnerDto dto) {
        return Owner.builder()
                .ownerId(dto.getOwnerId())
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .build();
    }
}