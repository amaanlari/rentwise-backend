package com.rentwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {
    private Long tenantId;
    private Long roomId;
    private String name;
    private String contact;
    private String email;
    private LocalDate joiningDate;
    private LocalDate exitDate;
    private String tenantType;  // PRIMARY / SECONDARY
}