package com.rentwise.ServiceImpl;

import com.rentwise.dto.TenantDto;
import com.rentwise.mapper.TenantMapper;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.repository.RoomRepository;
import com.rentwise.repository.TenantRepository;
import com.rentwise.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;

    @Override
    public TenantDto createTenant(TenantDto tenantDto) {
        Room room = roomRepository.findById(tenantDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + tenantDto.getRoomId()));
        Tenant tenant = TenantMapper.toEntity(tenantDto, room);
        return TenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantDto getTenant(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .map(TenantMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + tenantId));
    }

    @Override
    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream()
                .map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDto updateTenant(Long tenantId, TenantDto tenantDto) {
        Tenant existing = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + tenantId));
        existing.setName(tenantDto.getName());
        existing.setContact(tenantDto.getContact());
        existing.setEmail(tenantDto.getEmail());
        existing.setJoiningDate(tenantDto.getJoiningDate());
        existing.setExitDate(tenantDto.getExitDate());
        return TenantMapper.toDto(tenantRepository.save(existing));
    }

    @Override
    public void deleteTenant(Long tenantId) {
        if (!tenantRepository.existsById(tenantId)) {
            throw new RuntimeException("Tenant not found with id: " + tenantId);
        }
        tenantRepository.deleteById(tenantId);
    }
}
