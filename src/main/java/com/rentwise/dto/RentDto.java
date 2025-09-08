package com.rentwise.dto;

import com.rentwise.model.RentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {
    private Long rentId;
    private Long roomId;
    private Long tenantId;
    private LocalDate monthYear;
    private BigDecimal rentAmount;
    private String status;  // "DUE", "OVERDUE", "PAID", "PARTIALLY_PAID"
    private LocalDate dueDate;
    private LocalDate paidDate;
}