package com.rentwise.service;

import com.rentwise.dto.OwnerDto;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);
    OwnerDto getOwner(Long ownerId);
    List<OwnerDto> getAllOwners();
    OwnerDto updateOwner(Long ownerId, OwnerDto ownerDto);
    void deleteOwner(Long ownerId);
}