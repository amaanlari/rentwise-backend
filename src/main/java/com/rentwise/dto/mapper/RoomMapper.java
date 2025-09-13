package com.rentwise.dto.mapper;

import com.rentwise.dto.RoomDtos;
import com.rentwise.model.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomDtos.RoomResponse toResponse(Room room) {
        return RoomDtos.RoomResponse.builder()
                .id(room.getId())
                .ownerId(room.getOwner().getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .currentRentAmount(room.getCurrentRentAmount())
                .securityDeposit(room.getSecurityDeposit())
                .status(room.getStatus())
                .notes(room.getNotes())
                .build();
    }

    public RoomDtos.DeletedRoomResponse toDeletedResponse(Room room, String message) {
        return RoomDtos.DeletedRoomResponse.builder()
                .message(message)
                .room(toResponse(room))
                .build();
    }
}
