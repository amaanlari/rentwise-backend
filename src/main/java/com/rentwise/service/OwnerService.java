package com.rentwise.service;

import com.rentwise.dto.OwnerDtos;
import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.dto.OwnerDtos.OwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerUpdateRequest;

public interface OwnerService {
    OwnerResponse createOwner(OwnerRequest request);
    OwnerResponse getOwnerById(Long id);
    OwnerResponse updateOwner(Long id, OwnerUpdateRequest request);
    OwnerDtos.DeletedOwnerResponse deleteOwner(Long id);
}
