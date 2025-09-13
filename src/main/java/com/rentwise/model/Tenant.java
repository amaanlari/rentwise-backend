package com.rentwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Room.class)
    private Room room;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Column(nullable = false, unique = true)
    private String contactPhone;

    @Column(nullable = false, unique = true)
    private String idProofNumber;

    @Column(nullable = false)
    private String emergencyContact;

    private Instant joiningDate;
    private Instant exitDate;

    @Column(nullable = false)
    private Instant leaseStartDate;

    @Column(nullable = false)
    private Instant leaseEndDate;

    private String notes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
