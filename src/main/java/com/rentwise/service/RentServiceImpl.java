package com.rentwise.service;

import com.rentwise.dto.RentDtos;
import com.rentwise.dto.RentDtos.RentRequest;
import com.rentwise.dto.RentDtos.RentResponse;
import com.rentwise.dto.RentDtos.RentUpdateRequest;
import com.rentwise.dto.mapper.RentMapper;
import com.rentwise.exception.DuplicateDataException;
import com.rentwise.exception.ResourceNotFoundException;
import com.rentwise.model.Rent;
import com.rentwise.model.Room;
import com.rentwise.model.enums.RoomStatus;
import com.rentwise.repository.RentRepository;
import com.rentwise.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final RentMapper rentMapper;
    private final RoomRepository roomRepository;

    @Override
    public RentResponse createRent(RentRequest request) {
        log.info("Creating rent for roomId: {}", request.roomId());

        if (rentRepository.existsByRoomIdAndRentMonthAndRentYearAndDeletedIsFalse(request.roomId(), request.rentMonth(), request.rentYear())) {
            throw new DuplicateDataException("Rent already exists for this room and period");
        }

        Rent rent = new Rent();
        BeanUtils.copyProperties(request, rent);
        Room room = roomRepository.getRoomByIdAndDeletedFalse(request.roomId()).orElseThrow(() ->
                new ResourceNotFoundException("Room not found"));

        if (room.getStatus().equals(RoomStatus.INACTIVE)) {
            throw new IllegalStateException("Cannot create rent for an inactive room");
        }

        rent.setRoom(room);
        Rent saved = rentRepository.save(rent);
        log.debug("Rent saved with id: {}", saved.getId());
        return rentMapper.toResponse(saved);
    }

    @Override
    public RentResponse getRentById(Long id) {
        log.info("Fetching rent by id: {}", id);
        Rent rent = rentRepository.getRentByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found"));
        return rentMapper.toResponse(rent);
    }

    @Transactional
    @Override
    public RentResponse updateRent(Long id, RentUpdateRequest request) {
        log.info("Updating rent id: {}", id);
        Rent rent = rentRepository.getRentByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found"));
        BeanUtils.copyProperties(request, rent);
        Rent saved = rentRepository.save(rent);
        return rentMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public RentDtos.DeletedRentResponse deleteRent(Long id) {
        log.info("Soft deleting rent id: {}", id);
        Rent rent = rentRepository.getRentByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rent not found"));
        rent.setDeleted(true);
        return rentMapper.toDeletedResponse(rent, "Rent deleted successfully");
    }
}
