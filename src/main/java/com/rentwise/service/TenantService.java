package com.rentwise.service;

import com.rentwise.dto.TenantDto;
import com.rentwise.model.Tenant;
import com.rentwise.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TenantService {
    TenantDto createTenant(TenantDto dto);
    TenantDto getTenant(Long tenantId);
    List<TenantDto> getAllTenants();
}
