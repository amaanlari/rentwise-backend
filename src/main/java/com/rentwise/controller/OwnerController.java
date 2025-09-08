package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class OwnerController {

    @GetMapping(ApiEndpoints.CREATE_OWNER)
    public ResponseEntity<String> createOwner() {
        return ResponseEntity.ok("Owner created!");
    }
}
//"/rentwise/api/v1/owner/create"