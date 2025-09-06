package com.rentwise.service;

import com.rentwise.dto.RentDto;

import java.util.List;

public interface RentService {
    RentDto createRent(RentDto dto);
    RentDto getRent(Long rentId);
    List<RentDto> getAllRents();
}