package com.rentwise.service;

import com.rentwise.dto.RoomDtos;
import com.rentwise.dto.RoomDtos.RoomRequest;
import com.rentwise.dto.RoomDtos.RoomResponse;
import com.rentwise.dto.RoomDtos.RoomUpdateRequest;

public interface RoomService {
    RoomResponse createRoom(RoomRequest request);
    RoomResponse getRoomById(Long id);
    RoomResponse updateRoom(Long id, RoomUpdateRequest request);
    RoomDtos.DeletedRoomResponse deleteRoom(Long id);
}
