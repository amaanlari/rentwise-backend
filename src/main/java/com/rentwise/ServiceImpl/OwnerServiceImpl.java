package com.rentwise.ServiceImpl;

import com.rentwise.dto.OwnerDto;
import com.rentwise.mapper.OwnerMapper;
import com.rentwise.model.Owner;
import com.rentwise.repository.OwnerRepository;
import com.rentwise.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        Owner owner = OwnerMapper.toEntity(ownerDto);
        Owner saved = ownerRepository.save(owner);
        return OwnerMapper.toDto(saved);
    }

    @Override
    public OwnerDto getOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .map(OwnerMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(OwnerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerDto updateOwner(Long ownerId, OwnerDto ownerDto) {
        Owner existing = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id: " + ownerId));

        existing.setName(ownerDto.getName());
        existing.setContact(ownerDto.getContact());
        existing.setEmail(ownerDto.getEmail());

        return OwnerMapper.toDto(ownerRepository.save(existing));
    }

    @Override
    public void deleteOwner(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new RuntimeException("Owner not found with id: " + ownerId);
        }
        ownerRepository.deleteById(ownerId);
    }
}
