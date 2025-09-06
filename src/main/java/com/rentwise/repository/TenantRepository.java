package com.rentwise.repository;

import com.rentwise.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    List<Tenant> findByRoomRoomId(Long roomId);  // Get tenants in a room
}
