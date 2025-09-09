package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.dto.OwnerDtos.OwnerResponse;
import com.rentwise.dto.OwnerDtos.OwnerUpdateRequest;
import com.rentwise.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping(ApiEndpoints.CREATE_OWNER)
    public ResponseEntity<OwnerResponse> createOwner(@RequestBody OwnerRequest request) {
        return ResponseEntity.ok(ownerService.createOwner(request));
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
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
