package com.rentwise.service;

import com.rentwise.dto.RentDtos;
import com.rentwise.dto.RentDtos.RentRequest;
import com.rentwise.dto.RentDtos.RentResponse;
import com.rentwise.dto.RentDtos.RentUpdateRequest;

public interface RentService {
    RentResponse createRent(RentRequest request);
    RentResponse getRentById(Long id);
    RentResponse updateRent(Long id, RentUpdateRequest request);
    RentDtos.DeletedRentResponse deleteRent(Long id);
}
