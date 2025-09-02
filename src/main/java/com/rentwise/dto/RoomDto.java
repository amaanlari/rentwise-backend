package com.rentwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private String roomId;
    private String roomNumber;
    private Double currentRentAmount;
    private String ownerId;
}
