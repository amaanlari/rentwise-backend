package com.rentwise.service;

import com.rentwise.dto.TenantDtos;
import com.rentwise.dto.TenantDtos.TenantRequest;
import com.rentwise.dto.TenantDtos.TenantResponse;
import com.rentwise.dto.TenantDtos.TenantUpdateRequest;

public interface TenantService {
    TenantResponse createTenant(TenantRequest request);
    TenantResponse getTenantById(Long id);
    TenantResponse updateTenant(Long id, TenantUpdateRequest request);
    TenantDtos.DeletedTenantResponse deleteTenant(Long id);
}
