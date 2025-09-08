package com.rentwise.controller;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.service.TenantDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenant-documents")
@RequiredArgsConstructor
public class TenantDocumentController {

    private final TenantDocumentService tenantDocumentService;

    @PostMapping
    public ResponseEntity<TenantDocumentDto> upload(@RequestBody TenantDocumentDto dto) {
        return ResponseEntity.ok(tenantDocumentService.upload(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDocumentDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(tenantDocumentService.get(id));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<TenantDocumentDto>> listByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(tenantDocumentService.listByTenant(tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDocumentDto> update(
            @PathVariable Long id,
            @RequestBody TenantDocumentDto dto
    ) {
        return ResponseEntity.ok(tenantDocumentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenantDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
