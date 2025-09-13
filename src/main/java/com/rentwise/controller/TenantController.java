package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.TenantDtos;
import com.rentwise.dto.TenantDtos.TenantRequest;
import com.rentwise.dto.TenantDtos.TenantResponse;
import com.rentwise.dto.TenantDtos.TenantUpdateRequest;
import com.rentwise.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class TenantController {

    private final TenantService tenantService;

    @PostMapping(ApiEndpoints.CREATE_TENANT)
    public ResponseEntity<TenantResponse> createTenant(@RequestBody TenantRequest request) {
        TenantResponse created = tenantService.createTenant(request);
        return ResponseEntity.created(URI.create(ApiEndpoints.BASE_URL + ApiEndpoints.GET_TENANT_BY_ID
                .replace(ApiEndpoints.ID, created.id().toString()))).body(created);
    }

    @GetMapping(ApiEndpoints.GET_TENANT_BY_ID)
    public ResponseEntity<TenantResponse> getTenant(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PutMapping(ApiEndpoints.UPDATE_TENANT)
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable Long id, @RequestBody TenantUpdateRequest request) {
        return ResponseEntity.ok(tenantService.updateTenant(id, request));
    }

    @DeleteMapping(ApiEndpoints.DELETE_TENANT)
    public ResponseEntity<TenantDtos.DeletedTenantResponse> deleteTenant(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.deleteTenant(id));
    }
}
