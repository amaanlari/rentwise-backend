package com.rentwise.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, targetEntity = Room.class)
    Long roomId;
    String name;
    String contactEmail;
    String contactPhone;
    String idProofNumber;
    String emergencyContact;
    String joiningDate;
    String exitDate;
    String leaseStartDate;
    String leaseEndDate;
    String notes;
}
