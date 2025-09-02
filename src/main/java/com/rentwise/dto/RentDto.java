package com.rentwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {
    private String rentId;
    private String roomId;
    private LocalDate monthYear;
    private Double rentAmount;
    private String status;
    private LocalDate dueDate;
    private LocalDate paidDate;
}
