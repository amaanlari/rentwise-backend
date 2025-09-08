package com.rentwise.ServiceImpl;

import com.rentwise.dto.RentDto;
import com.rentwise.mapper.RentMapper;
import com.rentwise.model.Rent;
import com.rentwise.model.RentStatus;
import com.rentwise.model.Room;
import com.rentwise.repository.RentRepository;
import com.rentwise.repository.RoomRepository;
import com.rentwise.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentServiceImpl implements RentService {

    private final RentRepository rentRepository;
    private final RoomRepository roomRepository;

    @Override
    public RentDto createRent(RentDto rentDto) {
        Room room = roomRepository.findById(rentDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + rentDto.getRoomId()));
        Rent rent = RentMapper.toEntity(rentDto, room);
        return RentMapper.toDto(rentRepository.save(rent));
    }

    @Override
    public RentDto getRent(Long rentId) {
        return rentRepository.findById(rentId)
                .map(RentMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Rent not found with id: " + rentId));
    }

    @Override
    public List<RentDto> getAllRents() {
        return rentRepository.findAll().stream()
                .map(RentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RentDto updateRent(Long rentId, RentDto rentDto) {
        Rent existing = rentRepository.findById(rentId)
                .orElseThrow(() -> new RuntimeException("Rent not found with id: " + rentId));
        existing.setMonthYear(rentDto.getMonthYear());
        existing.setRentAmount(rentDto.getRentAmount());
        existing.setStatus(RentStatus.valueOf(rentDto.getStatus()));
        existing.setDueDate(rentDto.getDueDate());
        existing.setPaidDate(rentDto.getPaidDate());
        return RentMapper.toDto(rentRepository.save(existing));
    }

    @Override
    public void deleteRent(Long rentId) {
        if (!rentRepository.existsById(rentId)) {
            throw new RuntimeException("Rent not found with id: " + rentId);
        }
        rentRepository.deleteById(rentId);
    }
}
