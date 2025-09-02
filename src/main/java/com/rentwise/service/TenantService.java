package com.rentwise.service;

import com.rentwise.dto.TenantDto;
import com.rentwise.model.Tenant;
import com.rentwise.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TenantService {
    TenantDto createTenant(TenantDto dto);
    TenantDto getTenantById(String tenantId);
    List<TenantDto> getAllTenants();
    TenantDto updateTenant(String tenantId, TenantDto dto);
    void deleteTenant(String tenantId);
}
