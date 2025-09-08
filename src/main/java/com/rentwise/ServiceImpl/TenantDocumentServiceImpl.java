package com.rentwise.ServiceImpl;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.mapper.TenantDocumentMapper;
import com.rentwise.model.Tenant;
import com.rentwise.model.TenantDocument;
import com.rentwise.repository.TenantDocumentRepository;
import com.rentwise.repository.TenantRepository;
import com.rentwise.service.TenantDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantDocumentServiceImpl implements TenantDocumentService {

    private final TenantDocumentRepository tenantDocumentRepository;
    private final TenantRepository tenantRepository;

    @Override
    public TenantDocumentDto upload(TenantDocumentDto dto) {
        Tenant tenant = tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found with id: " + dto.getTenantId()));

        TenantDocument doc = TenantDocumentMapper.toEntity(dto, tenant);
        return TenantDocumentMapper.toDto(tenantDocumentRepository.save(doc));
    }

    @Override
    public TenantDocumentDto get(Long id) {
        return tenantDocumentRepository.findById(id)
                .map(TenantDocumentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("TenantDocument not found with id: " + id));
    }

    @Override
    public List<TenantDocumentDto> listByTenant(Long tenantId) {
        // Optional: verify tenant exists
        if (!tenantRepository.existsById(tenantId)) {
            throw new RuntimeException("Tenant not found with id: " + tenantId);
        }
        return tenantDocumentRepository.findByTenantTenantId(tenantId).stream()
                .map(TenantDocumentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDocumentDto update(Long id, TenantDocumentDto dto) {
        TenantDocument existing = tenantDocumentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TenantDocument not found with id: " + id));

        // Update editable fields (keep tenant the same; change only metadata/path)
        existing.setDocumentType(dto.getDocumentType());
        existing.setDocumentNumber(dto.getDocumentNumber());
        existing.setFilePath(dto.getFilePath());

        return TenantDocumentMapper.toDto(tenantDocumentRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!tenantDocumentRepository.existsById(id)) {
            throw new RuntimeException("TenantDocument not found with id: " + id);
        }
        tenantDocumentRepository.deleteById(id);
    }
}