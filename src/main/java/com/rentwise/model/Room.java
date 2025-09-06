package com.rentwise.model;

import com.rentwise.model.enums.RoomStatus;
import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, targetEntity = Owner.class)
    Long ownerId;
    String roomNumber;
    String roomType;
    Double currentRentAmount;
    Double securityDeposit;
    RoomStatus status;
    String notes;
}
