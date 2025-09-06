package com.rentwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tenant_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType;   // Aadhaar, VoterID, Passport
    private String documentNumber;
    private String filePath;       // optional scanned copy

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}