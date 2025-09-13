package com.rentwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Storing foreign key as scalar to keep implementation simple
    private Long roomId;
    private String name;
    private String contactEmail;
    private String contactPhone;
    private String idProofNumber;
    private String emergencyContact;
    private String joiningDate;
    private String exitDate;
    private String leaseStartDate;
    private String leaseEndDate;
    private String notes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
