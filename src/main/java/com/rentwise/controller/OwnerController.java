package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.OwnerDtos;
import com.rentwise.dto.OwnerDtos.OwnerRequest;
import com.rentwise.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class OwnerController {

    private final OwnerService service;

    @PostMapping(ApiEndpoints.CREATE_OWNER)
    public ResponseEntity<OwnerDtos.OwnerResponse> createOwner(@RequestBody OwnerRequest request) {
        return ResponseEntity.ok(service.createOwner(request));
    }
}
