package com.rentwise.model;

import com.rentwise.model.enums.RentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    Long id;

    @ManyToOne(optional = false, targetEntity = Room.class)
    Long roomId;
    Integer rentMonth;
    Integer rentYear;
    Double amount;
    RentStatus status;
    String dueDate;
    String paidDate;
    String notes;
}
