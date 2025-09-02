package com.rentwise.mapper;

import com.rentwise.dto.RoomDto;
import com.rentwise.model.Owner;
import com.rentwise.model.Room;

public class RoomMapper {
    public static RoomDto toDto(Room room) {
        if (room == null) return null;
        return RoomDto.builder()
                .roomId(room.getRoomId())
                .ownerId(room.getOwner().getOwnerId())
                .roomNumber(room.getRoomNumber())
                .currentRentAmount(room.getCurrentRentAmount())
                .build();
    }

    public static Room toEntity(RoomDto dto, Owner owner) {
        if (dto == null) return null;
        return Room.builder()
                .roomId(dto.getRoomId())
                .owner(owner)
                .roomNumber(dto.getRoomNumber())
                .currentRentAmount(dto.getCurrentRentAmount())
                .build();
    }
}