package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.OwnerDtos.DeletedOwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.dto.OwnerDtos.OwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerUpdateRequest;
import com.rentwise.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping(ApiEndpoints.CREATE_OWNER)
    public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerRequest request) {
        OwnerResponse createdOwnerResponse = ownerService.createOwner(request);
        return ResponseEntity.created(URI.create(ApiEndpoints.BASE_URL + ApiEndpoints.GET_OWNER_BY_ID
                .replace(ApiEndpoints.ID, createdOwnerResponse.id().toString()))).body(createdOwnerResponse);
    }

    @PutMapping(ApiEndpoints.UPDATE_OWNER)
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long id, @RequestBody OwnerUpdateRequest request) {
        return ResponseEntity.ok(ownerService.updateOwner(id, request));
    }

    @GetMapping(ApiEndpoints.GET_OWNER_BY_ID)
    public ResponseEntity<OwnerResponse> getOwner(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }

    @DeleteMapping(ApiEndpoints.DELETE_OWNER)
    public ResponseEntity<DeletedOwnerResponse> deleteOwner(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.deleteOwner(id));
    }
}
