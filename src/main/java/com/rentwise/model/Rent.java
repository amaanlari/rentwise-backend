package com.rentwise.model;

import com.rentwise.model.enums.RentStatus;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uc_rent_room_month_year", columnNames = {"room_id", "rent_month", "rent_year"})
})
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
            targetEntity = Room.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            optional = false
    )
    private Room room;

    @Column(nullable = false)
    private Integer rentMonth;

    @Column(nullable = false)
    private Integer rentYear;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private RentStatus status;

    @Column(nullable = false)
    private String dueDate;

    private String paidDate;
    private String notes;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;
}
