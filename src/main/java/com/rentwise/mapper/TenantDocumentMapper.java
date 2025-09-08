package com.rentwise.mapper;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.model.Tenant;
import com.rentwise.model.TenantDocument;

public class TenantDocumentMapper {

    public static TenantDocumentDto toDto(TenantDocument doc) {
        return TenantDocumentDto.builder()
                .id(doc.getId())
                .tenantId(doc.getTenant().getTenantId())
                .documentType(doc.getDocumentType())
                .documentNumber(doc.getDocumentNumber())
                .filePath(doc.getFilePath())
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