package com.rentwise.service;

import com.rentwise.dto.RentDtos;
import com.rentwise.dto.RentDtos.RentRequest;
import com.rentwise.dto.RentDtos.RentResponse;
import com.rentwise.dto.RentDtos.RentUpdateRequest;
import com.rentwise.dto.mapper.RentMapper;
import com.rentwise.exception.ResourceNotFoundException;
import com.rentwise.model.Rent;
import com.rentwise.repository.RentRepository;
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

    @Override
    public RentResponse createRent(RentRequest request) {
        log.info("Creating rent for roomId: {}", request.roomId());
        Rent rent = new Rent();
        BeanUtils.copyProperties(request, rent);
        Rent saved = rentRepository.save(rent);
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
