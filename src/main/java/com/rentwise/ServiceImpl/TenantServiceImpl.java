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
public class TenantServiceImpl implements TenantService {
    @Override
    public TenantDto createTenant(TenantDto dto) { System.out.println("Creating tenant: "+dto); return dto; }
    @Override
    public TenantDto getTenant(Long id){ System.out.println("Fetching tenant: "+id); return TenantDto.builder().tenantId(id).build(); }
    @Override
    public List<TenantDto> getAllTenants(){ return new ArrayList<>(); }
}