package com.rentwise.service;

import com.rentwise.dto.RoomDtos;
import com.rentwise.dto.RoomDtos.RoomRequest;
import com.rentwise.dto.RoomDtos.RoomResponse;
import com.rentwise.dto.RoomDtos.RoomUpdateRequest;
import com.rentwise.dto.mapper.RoomMapper;
import com.rentwise.exception.ResourceNotFoundException;
import com.rentwise.model.Owner;
import com.rentwise.model.Room;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final OwnerRepository ownerRepository;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Override
    public RoomResponse createRoom(RoomRequest request) {
        log.info("Creating room for ownerId: {}", request.ownerId());
        Room room = new Room();
        BeanUtils.copyProperties(request, room);
        log.info("Creating room: {}", room);
        Owner currentOwner = ownerRepository.getOwnerByIdAndDeletedFalse(request.ownerId())
                        .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        room.setOwner(currentOwner);
        Room saved = roomRepository.save(room);
        log.debug("Room saved with ID: {}", saved.getId());
        return roomMapper.toResponse(saved);
    }

    @Override
    public RoomResponse getRoomById(Long id) {
        log.info("Fetching room by id: {}", id);
        Room room = roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return roomMapper.toResponse(room);
    }

    @Transactional
    @Override
    public RoomResponse updateRoom(Long id, RoomUpdateRequest request) {
        log.info("Updating room id: {}", id);
        Room room = roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        BeanUtils.copyProperties(request, room);
        Room saved = roomRepository.save(room);
        log.debug("Room {} updated successfully", id);
        return roomMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public RoomDtos.DeletedRoomResponse deleteRoom(Long id) {
        log.info("Soft deleting room id: {}", id);
        Room room = roomRepository.getRoomByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        room.setDeleted(true);
        log.debug("Room {} marked as deleted", id);
        return roomMapper.toDeletedResponse(room, "Room deleted successfully");
    }
}
