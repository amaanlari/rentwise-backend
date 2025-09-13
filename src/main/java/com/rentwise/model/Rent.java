package com.rentwise.model;

import com.rentwise.model.enums.RentStatus;
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
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Storing foreign key as scalar to keep implementation simple
    private Long roomId;
    private Integer rentMonth;
    private Integer rentYear;
    private Double amount;
    private RentStatus status;
    private String dueDate;
    private String paidDate;
    private String notes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
