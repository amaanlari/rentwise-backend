package com.rentwise.mapper;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.model.Tenant;
import com.rentwise.model.TenantDocument;

public class TenantDocumentMapper {
    public static TenantDocumentDto toDto(TenantDocument document) {
        return TenantDocumentDto.builder()
                .id(document.getId())
                .tenantId(document.getTenant().getTenantId())
                .documentType(document.getDocumentType())
                .documentNumber(document.getDocumentNumber())
                .filePath(document.getFilePath())
                .build();
    }

    public static TenantDocument toEntity(TenantDocumentDto dto, Tenant tenant) {
        return TenantDocument.builder()
                .id(dto.getId())
                .tenant(tenant)
                .documentType(dto.getDocumentType())
                .documentNumber(dto.getDocumentNumber())
                .filePath(dto.getFilePath())
                .build();
    }
}
