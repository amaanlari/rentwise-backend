package com.rentwise.service;

import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.dto.OwnerDtos.OwnerResponse;

public interface OwnerService {
    OwnerResponse createOwner(OwnerRequest request);
    OwnerResponse getOwnerById(String id);
    OwnerResponse updateOwner(String id, OwnerRequest request);
    OwnerResponse deleteOwner(String id);
}
