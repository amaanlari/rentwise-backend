package com.rentwise.mapper;

import com.rentwise.dto.TenantDto;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.model.TenantType;

public class TenantMapper {
    public static TenantDto toDto(Tenant tenant) {
        return TenantDto.builder()
                .tenantId(tenant.getTenantId())
                .roomId(tenant.getRoom() != null ? tenant.getRoom().getRoomId() : null)
                .name(tenant.getName())
                .contact(tenant.getContact())
                .email(tenant.getEmail())
                .joiningDate(tenant.getJoiningDate())
                .exitDate(tenant.getExitDate())
                .tenantType(tenant.getTenantType() != null ? tenant.getTenantType().name() : null)
                .build();
    }

    public static Tenant toEntity(TenantDto dto, Room room) {
        return Tenant.builder()
                .tenantId(dto.getTenantId())
                .room(room)
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .joiningDate(dto.getJoiningDate())
                .exitDate(dto.getExitDate())
                .tenantType(dto.getTenantType() != null ? TenantType.valueOf(dto.getTenantType()) : null)
                .build();
    }
}