package com.rentwise.service;

import com.rentwise.dto.OwnerDtos;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;

    @Override
    public OwnerDtos.OwnerResponse createOwner(OwnerDtos.OwnerRequest request) {
        Owner owner = Owner.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .build();

        Owner savedOwner = ownerRepository.save(owner);

        log.debug("savedOwner: {}", savedOwner);

        return OwnerDtos.OwnerResponse.builder()
                .id(savedOwner.getId())
                .name(savedOwner.getName())
                .email(savedOwner.getEmail())
                .phoneNumber(savedOwner.getPhoneNumber())
                .build();
    }

    @Override
    public OwnerDtos.OwnerResponse getOwnerById(String id) {
        return null;
    }

    @Override
    public OwnerDtos.OwnerResponse updateOwner(String id, OwnerDtos.OwnerRequest request) {
        return null;
    }

    @Override
    public OwnerDtos.OwnerResponse deleteOwner(String id) {
        return null;
    }
}
