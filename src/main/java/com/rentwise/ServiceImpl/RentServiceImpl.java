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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentServiceImpl implements RentService {
    @Override
    public RentDto createRent(RentDto dto){ System.out.println("Creating rent: "+dto); return dto;}
    @Override
    public RentDto getRent(Long id){ System.out.println("Fetching rent: "+id); return RentDto.builder().rentId(id).build();}
    @Override
    public List<RentDto> getAllRents(){ return new ArrayList<>(); }
}