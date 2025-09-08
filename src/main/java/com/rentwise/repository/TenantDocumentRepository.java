package com.rentwise.repository;

import com.rentwise.model.TenantDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TenantDocumentRepository extends JpaRepository<TenantDocument, Long> {
    List<TenantDocument> findByTenantTenantId(Long tenantId);
}