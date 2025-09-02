package com.rentwise.mapper;

import com.rentwise.dto.OwnerDto;
import com.rentwise.model.Owner;

public class OwnerMapper {
    public static OwnerDto toDto(Owner owner) {
        if (owner == null) return null;
        return OwnerDto.builder()
                .ownerId(owner.getOwnerId())
                .name(owner.getName())
                .contact(owner.getContact())
                .email(owner.getEmail())
                .build();
    }

    public static Owner toEntity(OwnerDto dto) {
        if (dto == null) return null;
        return Owner.builder()
                .ownerId(dto.getOwnerId())
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .build();
    }
}