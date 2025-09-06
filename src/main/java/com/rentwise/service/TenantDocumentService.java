package com.rentwise.service;

import com.rentwise.dto.TenantDocumentDto;

import java.util.List;

public interface TenantDocumentService {
    TenantDocumentDto createDocument(TenantDocumentDto dto);
    List<TenantDocumentDto> getDocumentsByTenant(Long tenantId);
}

