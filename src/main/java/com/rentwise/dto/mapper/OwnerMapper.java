package com.rentwise.dto.mapper;

import com.rentwise.dto.OwnerDtos;
import com.rentwise.dto.OwnerDtos.OwnerResponse;
import com.rentwise.model.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public OwnerResponse toResponse(Owner owner) {
        return OwnerResponse.builder()
                .id(owner.getId())
                .name(owner.getName())
                .email(owner.getEmail())
                .phoneNumber(owner.getPhoneNumber())
                .build();
    }

    public OwnerDtos.DeletedOwnerResponse toDeletedResponse(Owner owner, String message) {
        return OwnerDtos.DeletedOwnerResponse.builder()
                .message(message)
                .owner(toResponse(owner))
                .build();
    }
}
