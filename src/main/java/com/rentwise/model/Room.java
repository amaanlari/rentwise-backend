package com.rentwise.model;

import com.rentwise.model.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Owner.class)
    private Owner owner;

    @Column(nullable = false)
    private String roomNumber;

    private String roomType;

    @Column(nullable = false)
    private Double currentRentAmount;

    private Double securityDeposit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private String notes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;
}
