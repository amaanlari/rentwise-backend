package com.rentwise.service;

import com.rentwise.dto.OwnerDto;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OwnerService {
    OwnerDto createOwner(OwnerDto ownerDto);
    OwnerDto getOwnerById(String ownerId);
    List<OwnerDto> getAllOwners();
    OwnerDto updateOwner(String ownerId, OwnerDto ownerDto);
    void deleteOwner(String ownerId);
}