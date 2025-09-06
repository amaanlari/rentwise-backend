package com.rentwise.ServiceImpl;

import com.rentwise.dto.TenantDocumentDto;
import com.rentwise.service.TenantDocumentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenantDocumentServiceImpl implements TenantDocumentService {
    @Override
    public TenantDocumentDto createDocument(TenantDocumentDto dto){ System.out.println("Creating doc: "+dto); return dto;}
    @Override
    public List<TenantDocumentDto> getDocumentsByTenant(Long tenantId){ System.out.println("Fetching docs for tenant: "+tenantId); return new ArrayList<>(); }
}
