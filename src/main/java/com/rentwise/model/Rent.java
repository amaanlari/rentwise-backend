package com.rentwise.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    @Id
    private String rentId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private LocalDate monthYear;
    private Double rentAmount;
    private String status;
    private LocalDate dueDate;
    private LocalDate paidDate;
}