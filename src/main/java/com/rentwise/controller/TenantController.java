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

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantDto> createTenant(@RequestBody TenantDto dto) {
        return ResponseEntity.ok(tenantService.createTenant(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDto> getTenantById(@PathVariable String id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @GetMapping
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> updateTenant(@PathVariable String id, @RequestBody TenantDto dto) {
        return ResponseEntity.ok(tenantService.updateTenant(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenant(@PathVariable String id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.noContent().build();
    }
}