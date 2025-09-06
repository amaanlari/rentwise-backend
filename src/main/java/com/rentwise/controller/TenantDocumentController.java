package com.rentwise.controller;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.service.TenantDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant-docs")
@RequiredArgsConstructor
public class TenantDocumentController {
    private final TenantDocumentService service;
    @PostMapping
    public TenantDocumentDto create(@RequestBody TenantDocumentDto dto){ return service.createDocument(dto);}
    @GetMapping("/tenant/{tenantId}") public List<TenantDocumentDto> getByTenant(@PathVariable Long tenantId){ return service.getDocumentsByTenant(tenantId);}
}
