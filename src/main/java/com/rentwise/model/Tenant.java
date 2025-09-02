package com.rentwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tenants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {
    @Id
    private String tenantId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private String name;
    private String contact;
    private String email;
    private LocalDate joiningDate;
    private LocalDate exitDate;
}