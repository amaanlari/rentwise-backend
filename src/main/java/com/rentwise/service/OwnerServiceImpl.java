package com.rentwise.service;

import com.rentwise.dto.OwnerDtos.DeletedOwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.dto.OwnerDtos.OwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerUpdateRequest;
import com.rentwise.dto.mapper.OwnerMapper;
import com.rentwise.exception.DuplicateDataException;
import com.rentwise.exception.ResourceNotFoundException;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService{

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public OwnerResponse createOwner(OwnerRequest request) {
        log.info("Creating new owner with email: {}", request.email());
        Owner owner = new Owner();

        BeanUtils.copyProperties(request, owner);

        if (ownerRepository.existsByEmail(request.email()))
            throw new DuplicateDataException("User with email already exists");
        if (ownerRepository.existsByPhoneNumber(request.phoneNumber()))
            throw new DuplicateDataException("User with phone number already exists");

        Owner saved = ownerRepository.save(owner);
        log.debug("Owner saved with ID: {}", saved.getId());

        return ownerMapper.toResponse(saved);
    }

    @Override
    public OwnerResponse getOwnerById(Long id) {
        log.info("Fetching owner with ID: {}", id);
        return this.ownerRepository.getOwnerByIdAndDeletedFalse(id)
                .map(ownerMapper::toResponse)
                .orElseThrow(() -> {
                    log.warn("Owner with ID {} not found or deleted", id);
                    return new ResourceNotFoundException("Owner not found");
                });
    }

    @Transactional
    @Override
    public OwnerResponse updateOwner(Long id, OwnerUpdateRequest request) {
        log.info("Updating owner with ID: {}", id);
        Owner owner = ownerRepository.getOwnerByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        BeanUtils.copyProperties(request, owner);

        log.debug("Owner {} updated successfully", id);
        return ownerMapper.toResponse(owner);
    }

    @Transactional
    @Override
    public DeletedOwnerResponse deleteOwner(Long id) {
        log.info("Soft deleting owner with ID: {}", id);
        Owner owner = ownerRepository.getOwnerByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        owner.setDeleted(true);
        log.debug("Owner {} marked as deleted", id);

        return ownerMapper.toDeletedResponse(owner, "Owner deleted successfully");
    }
}
