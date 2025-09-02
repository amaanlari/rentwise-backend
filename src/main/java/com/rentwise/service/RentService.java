package com.rentwise.service;

import com.rentwise.dto.RentDto;
import com.rentwise.model.Rent;
import com.rentwise.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RentService {
    RentDto createRent(RentDto dto);
    RentDto getRentById(String rentId);
    List<RentDto> getAllRents();
    RentDto updateRent(String rentId, RentDto dto);
    void deleteRent(String rentId);
}