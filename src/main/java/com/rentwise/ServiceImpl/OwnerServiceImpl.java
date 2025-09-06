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
public class OwnerServiceImpl implements OwnerService {

    @Override
    public OwnerDto createOwner(OwnerDto ownerDto) {
        System.out.println("Creating owner: " + ownerDto);
        return ownerDto;
    }

    @Override
    public OwnerDto getOwner(Long ownerId) {
        System.out.println("Fetching owner: " + ownerId);
        return OwnerDto.builder().ownerId(ownerId).name("Test Owner").build();
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        System.out.println("Fetching all owners");
        return new ArrayList<>();
    }

    @Override
    public OwnerDto updateOwner(Long ownerId, OwnerDto ownerDto) {
        System.out.println("Updating owner: " + ownerId);
        return ownerDto;
    }

    @Override
    public void deleteOwner(Long ownerId) {
        System.out.println("Deleting owner: " + ownerId);
    }
}