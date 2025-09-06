package com.rentwise.mapper;

import com.rentwise.dto.RentDto;
import com.rentwise.model.Rent;
import com.rentwise.model.RentStatus;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;

public class RentMapper {
    public static RentDto toDto(Rent rent) {
        return RentDto.builder()
                .rentId(rent.getRentId())
                .roomId(rent.getRoom() != null ? rent.getRoom().getRoomId() : null)
                .monthYear(rent.getMonthYear())
                .rentAmount(rent.getRentAmount())
                .status(rent.getStatus() != null ? rent.getStatus().name() : null)
                .dueDate(rent.getDueDate())
                .paidDate(rent.getPaidDate())
                .build();
    }

    public static Rent toEntity(RentDto dto, Room room) {
        return Rent.builder()
                .rentId(dto.getRentId())
                .room(room)
                .monthYear(dto.getMonthYear())
                .rentAmount(dto.getRentAmount())
                .status(dto.getStatus() != null ? RentStatus.valueOf(dto.getStatus()) : null)
                .dueDate(dto.getDueDate())
                .paidDate(dto.getPaidDate())
                .build();
    }
}
