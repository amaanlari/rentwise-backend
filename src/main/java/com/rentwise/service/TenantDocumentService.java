package com.rentwise.service;

import com.rentwise.dto.TenantDocumentDto;

import java.util.List;


import com.rentwise.dto.TenantDocumentDto;
import java.util.List;

public interface TenantDocumentService {
    TenantDocumentDto upload(TenantDocumentDto dto);
    TenantDocumentDto get(Long id);
    List<TenantDocumentDto> listByTenant(Long tenantId);
    TenantDocumentDto update(Long id, TenantDocumentDto dto);
    void delete(Long id);
}
