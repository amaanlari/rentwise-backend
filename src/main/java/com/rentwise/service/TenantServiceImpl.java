package com.rentwise.service;

import com.rentwise.dto.TenantDtos;
import com.rentwise.dto.TenantDtos.TenantRequest;
import com.rentwise.dto.TenantDtos.TenantResponse;
import com.rentwise.dto.TenantDtos.TenantUpdateRequest;
import com.rentwise.dto.mapper.TenantMapper;
import com.rentwise.exception.ResourceNotFoundException;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.repository.RoomRepository;
import com.rentwise.repository.TenantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final RoomRepository roomRepository;
    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    @Override
    public TenantResponse createTenant(TenantRequest request) {
        log.info("Creating tenant for roomId: {}", request.roomId());
        Tenant tenant = new Tenant();
        BeanUtils.copyProperties(request, tenant);
        Room room = roomRepository.getRoomByIdAndDeletedFalse(request.roomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room not found"));
        tenant.setRoom(room);
        Tenant saved = tenantRepository.save(tenant);
        return tenantMapper.toResponse(saved);
    }

    @Override
    public TenantResponse getTenantById(Long id) {
        log.info("Fetching tenant by id: {}", id);
        Tenant tenant = tenantRepository.getTenantByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        return tenantMapper.toResponse(tenant);
    }

    @Transactional
    @Override
    public TenantResponse updateTenant(Long id, TenantUpdateRequest request) {
        log.info("Updating tenant id: {}", id);
        Tenant tenant = tenantRepository.getTenantByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        BeanUtils.copyProperties(request, tenant);
        Tenant saved = tenantRepository.save(tenant);
        return tenantMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public TenantDtos.DeletedTenantResponse deleteTenant(Long id) {
        log.info("Soft deleting tenant id: {}", id);
        Tenant tenant = tenantRepository.getTenantByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));
        tenant.setDeleted(true);
        return tenantMapper.toDeletedResponse(tenant, "Tenant deleted successfully");
    }
}
