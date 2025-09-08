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
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Owner owner = ownerRepository.findById(roomDto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + roomDto.getOwnerId()));
        Room room = RoomMapper.toEntity(roomDto, owner);
        return RoomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public RoomDto getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .map(RoomMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto updateRoom(Long roomId, RoomDto roomDto) {
        Room existing = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
        existing.setRoomNumber(roomDto.getRoomNumber());
        existing.setCurrentRentAmount(roomDto.getCurrentRentAmount());
        return RoomMapper.toDto(roomRepository.save(existing));
    }

    @Override
    public void deleteRoom(Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
        roomRepository.deleteById(roomId);
    }
}