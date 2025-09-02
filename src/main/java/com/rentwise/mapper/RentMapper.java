package com.rentwise.mapper;

import com.rentwise.dto.RentDto;
import com.rentwise.model.Rent;
import com.rentwise.model.Room;
import com.rentwise.model.Tenant;

public class RentMapper {
    public static RentDto toDto(Rent rent) {
        if (rent == null) return null;
        return RentDto.builder()
                .rentId(rent.getRentId())
                .roomId(rent.getRoom().getRoomId())
                .monthYear(rent.getMonthYear())
                .rentAmount(rent.getRentAmount())
                .status(rent.getStatus())
                .dueDate(rent.getDueDate())
                .paidDate(rent.getPaidDate())
                .build();
    }

    public static Rent toEntity(RentDto dto, Room room) {
        if (dto == null) return null;
        return Rent.builder()
                .rentId(dto.getRentId())
                .room(room)
                .monthYear(dto.getMonthYear())
                .rentAmount(dto.getRentAmount())
                .status(dto.getStatus())
                .dueDate(dto.getDueDate())
                .paidDate(dto.getPaidDate())
                .build();
    }
}
