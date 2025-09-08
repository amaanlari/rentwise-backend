package com.rentwise.service;

import com.rentwise.dto.RentDto;

import java.util.List;

public interface RentService {
    RentDto createRent(RentDto rentDto);
    RentDto getRent(Long rentId);
    List<RentDto> getAllRents();
    RentDto updateRent(Long rentId, RentDto rentDto);
    void deleteRent(Long rentId);
}