package com.rentwise.mapper;

import com.rentwise.dto.RentDto;
import com.rentwise.dto.TenantDto;
import com.rentwise.model.Rent;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;

public class TenantMapper {
    public static TenantDto toDto(Tenant tenant) {
        if (tenant == null) return null;
        return TenantDto.builder()
                .tenantId(tenant.getTenantId())
                .roomId(tenant.getRoom().getRoomId())
                .name(tenant.getName())
                .contact(tenant.getContact())
                .email(tenant.getEmail())
                .joiningDate(tenant.getJoiningDate())
                .exitDate(tenant.getExitDate())
                .build();
    }

    public static Tenant toEntity(TenantDto dto, Room room) {
        if (dto == null) return null;
        return Tenant.builder()
                .tenantId(dto.getTenantId())
                .room(room)
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .joiningDate(dto.getJoiningDate())
                .exitDate(dto.getExitDate())
                .build();
    }
}