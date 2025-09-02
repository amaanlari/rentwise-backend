package com.rentwise.ServiceImpl;

import com.rentwise.dto.TenantDto;
import com.rentwise.mapper.TenantMapper;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;
import com.rentwise.repository.RoomRepository;
import com.rentwise.repository.TenantRepository;
import com.rentwise.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;

    @Override
    public TenantDto createTenant(TenantDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found: " + dto.getRoomId()));
        Tenant tenant = TenantMapper.toEntity(dto, room);
        return TenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantDto getTenantById(String tenantId) {
        return tenantRepository.findById(tenantId)
                .map(TenantMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + tenantId));
    }

    @Override
    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll()
                .stream().map(TenantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDto updateTenant(String tenantId, TenantDto dto) {
        Tenant existing = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + tenantId));

        // Room change
        if (dto.getRoomId() != null) {
            String currentRoomId = existing.getRoom() != null ? existing.getRoom().getRoomId() : null;
            if (!dto.getRoomId().equals(currentRoomId)) {
                Room newRoom = roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Room not found: " + dto.getRoomId()));
                existing.setRoom(newRoom);
            }
        }

        // Other fields
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getContact() != null) existing.setContact(dto.getContact());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getJoiningDate() != null) existing.setJoiningDate(dto.getJoiningDate());
        if (dto.getExitDate() != null) existing.setExitDate(dto.getExitDate());

        return TenantMapper.toDto(tenantRepository.save(existing));
    }

    @Override
    public void deleteTenant(String tenantId) {
        tenantRepository.deleteById(tenantId);
    }
}