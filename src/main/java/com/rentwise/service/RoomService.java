package com.rentwise.service;

import com.rentwise.dto.RoomDto;
import com.rentwise.model.Room;
import com.rentwise.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoomService {
    RoomDto createRoom(RoomDto dto);
    RoomDto getRoomById(String roomId);
    List<RoomDto> getAllRooms();
    RoomDto updateRoom(String roomId, RoomDto dto);
    void deleteRoom(String roomId);
}
