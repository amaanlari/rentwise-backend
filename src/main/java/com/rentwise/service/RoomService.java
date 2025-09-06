package com.rentwise.service;

import com.rentwise.dto.RoomDto;
import com.rentwise.model.Room;
import com.rentwise.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoomService {
    RoomDto createRoom(RoomDto dto);
    RoomDto getRoom(Long roomId);
    List<RoomDto> getAllRooms();
}