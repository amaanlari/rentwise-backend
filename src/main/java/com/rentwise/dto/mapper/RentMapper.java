package com.rentwise.dto.mapper;

import com.rentwise.dto.RentDtos;
import com.rentwise.model.Rent;
import org.springframework.stereotype.Component;

@Component
public class RentMapper {
    public RentDtos.RentResponse toResponse(Rent rent) {
        return RentDtos.RentResponse.builder()
                .id(rent.getId())
                .roomId(rent.getRoom().getId())
                .rentMonth(rent.getRentMonth())
                .rentYear(rent.getRentYear())
                .amount(rent.getAmount())
                .status(rent.getStatus())
                .dueDate(rent.getDueDate())
                .paidDate(rent.getPaidDate())
                .notes(rent.getNotes())
                .build();
    }

    public RentDtos.DeletedRentResponse toDeletedResponse(Rent rent, String message) {
        return RentDtos.DeletedRentResponse.builder()
                .message(message)
                .rent(toResponse(rent))
                .build();
    }
}
