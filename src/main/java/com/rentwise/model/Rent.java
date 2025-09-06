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
    private Long rentId;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    private LocalDate monthYear;
    private BigDecimal rentAmount;
    @Enumerated(EnumType.STRING)
    private RentStatus status;
    private LocalDate dueDate;
    private LocalDate paidDate;

}