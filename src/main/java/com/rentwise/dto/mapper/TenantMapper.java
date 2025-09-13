package com.rentwise.dto.mapper;

import com.rentwise.dto.TenantDtos;
import com.rentwise.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {
    public TenantDtos.TenantResponse toResponse(Tenant tenant) {
        return TenantDtos.TenantResponse.builder()
                .id(tenant.getId())
                .roomId(tenant.getRoom().getId())
                .name(tenant.getName())
                .contactEmail(tenant.getContactEmail())
                .contactPhone(tenant.getContactPhone())
                .idProofNumber(tenant.getIdProofNumber())
                .emergencyContact(tenant.getEmergencyContact())
                .joiningDate(tenant.getJoiningDate())
                .exitDate(tenant.getExitDate())
                .leaseStartDate(tenant.getLeaseStartDate())
                .leaseEndDate(tenant.getLeaseEndDate())
                .notes(tenant.getNotes())
                .build();
    }

    public TenantDtos.DeletedTenantResponse toDeletedResponse(Tenant tenant, String message) {
        return TenantDtos.DeletedTenantResponse.builder()
                .message(message)
                .tenant(toResponse(tenant))
                .build();
    }
}
