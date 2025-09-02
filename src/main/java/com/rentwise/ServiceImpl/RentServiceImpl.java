package com.rentwise.ServiceImpl;

import com.rentwise.dto.RentDto;
import com.rentwise.mapper.RentMapper;
import com.rentwise.model.Rent;
import com.rentwise.model.Room;
import com.rentwise.repository.RentRepository;
import com.rentwise.repository.RoomRepository;
import com.rentwise.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final RoomRepository roomRepository;

    @Override
    public RentDto createRent(RentDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found: " + dto.getRoomId()));
        Rent rent = RentMapper.toEntity(dto, room);
        return RentMapper.toDto(rentRepository.save(rent));
    }

    @Override
    public RentDto getRentById(String rentId) {
        return rentRepository.findById(rentId)
                .map(RentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Rent not found: " + rentId));
    }

    @Override
    public List<RentDto> getAllRents() {
        return rentRepository.findAll()
                .stream().map(RentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDto updateRent(String rentId, RentDto dto) {
        Rent existing = rentRepository.findById(rentId)
                .orElseThrow(() -> new RuntimeException("Rent not found: " + rentId));

        // Room change
        if (dto.getRoomId() != null) {
            String currentRoomId = existing.getRoom() != null ? existing.getRoom().getRoomId() : null;
            if (!dto.getRoomId().equals(currentRoomId)) {
                Room newRoom = roomRepository.findById(dto.getRoomId())
                        .orElseThrow(() -> new RuntimeException("Room not found: " + dto.getRoomId()));
                existing.setRoom(newRoom);
            }
        }

        // Other fields
        if (dto.getMonthYear() != null) existing.setMonthYear(dto.getMonthYear());
        if (dto.getRentAmount() != null) existing.setRentAmount(dto.getRentAmount());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getDueDate() != null) existing.setDueDate(dto.getDueDate());
        if (dto.getPaidDate() != null) existing.setPaidDate(dto.getPaidDate());

        return RentMapper.toDto(rentRepository.save(existing));
    }

    @Override
    public void deleteRent(String rentId) {
        rentRepository.deleteById(rentId);
    }
}