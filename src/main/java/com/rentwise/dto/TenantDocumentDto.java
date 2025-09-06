package com.rentwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantDocumentDto {
    private Long id;
    private Long tenantId;
    private String documentType;   // Aadhaar, Voter ID, Passport, etc.
    private String documentNumber;
    private String filePath;
}