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
@Table(name = "tenant", indexes = {
        @Index(name = "idx_tenant_room_id", columnList = "room_id, deleted"),
        @Index(name = "idx_tenant_contact_email", columnList = "contact_email, deleted"),
        @Index(name = "idx_tenant_contact_phone", columnList = "contact_phone_number, deleted")
})
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
    private String contactPhoneNumber;

    @Column(nullable = false, unique = true)
    private String idProofNumber;

    @Column(nullable = false)
    private String emergencyContactNumber;

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
