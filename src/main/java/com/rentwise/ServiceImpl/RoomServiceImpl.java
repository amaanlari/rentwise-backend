package com.rentwise.ServiceImpl;

import com.rentwise.dto.RoomDto;
import com.rentwise.mapper.RoomMapper;
import com.rentwise.model.Owner;
import com.rentwise.model.Room;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.repository.RoomRepository;
import com.rentwise.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public RoomDto createRoom(RoomDto dto) {
        System.out.println("Creating room: " + dto);
        return dto;
    }
    @Override
    public RoomDto getRoom(Long roomId) {
        System.out.println("Fetching room: " + roomId);
        return RoomDto.builder().roomId(roomId).build();
    }
    @Override
    public List<RoomDto> getAllRooms() { return new ArrayList<>(); }
}