package com.rentwise.controller;

import com.rentwise.dto.TenantDto;
import com.rentwise.mapper.TenantMapper;
import com.rentwise.model.Tenant;
import com.rentwise.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {
    private final TenantService service;
    @PostMapping public TenantDto create(@RequestBody TenantDto dto){ return service.createTenant(dto);}
    @GetMapping("/{id}") public TenantDto get(@PathVariable Long id){ return service.getTenant(id);}
    @GetMapping public List<TenantDto> getAll(){ return service.getAllTenants();}
}