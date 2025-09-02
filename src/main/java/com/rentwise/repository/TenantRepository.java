package com.rentwise.repository;

import com.rentwise.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TenantRepository extends JpaRepository<Tenant, String> {}
