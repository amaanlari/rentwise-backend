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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public RoomDto createRoom(RoomDto dto) {
        Owner owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found: " + dto.getOwnerId()));
        Room room = RoomMapper.toEntity(dto, owner);
        return RoomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public RoomDto getRoomById(String roomId) {
        return roomRepository.findById(roomId)
                .map(RoomMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream().map(RoomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto updateRoom(String roomId, RoomDto dto) {
        Room existing = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        // Update owner if provided & changed
        if (dto.getOwnerId() != null) {
            String currentOwnerId = existing.getOwner() != null ? existing.getOwner().getOwnerId() : null;
            if (!dto.getOwnerId().equals(currentOwnerId)) {
                Owner newOwner = ownerRepository.findById(dto.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Owner not found: " + dto.getOwnerId()));
                existing.setOwner(newOwner);
            }
        }

        // Update other fields
        if (dto.getRoomNumber() != null) existing.setRoomNumber(dto.getRoomNumber());
        if (dto.getCurrentRentAmount() != null) existing.setCurrentRentAmount(dto.getCurrentRentAmount());

        return RoomMapper.toDto(roomRepository.save(existing));
    }

    @Override
    public void deleteRoom(String roomId) {
        roomRepository.deleteById(roomId);
    }
}