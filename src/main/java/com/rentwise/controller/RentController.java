package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.RentDtos;
import com.rentwise.dto.RentDtos.RentRequest;
import com.rentwise.dto.RentDtos.RentResponse;
import com.rentwise.dto.RentDtos.RentUpdateRequest;
import com.rentwise.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class RentController {

    private final RentService rentService;

    @PostMapping(ApiEndpoints.CREATE_RENT)
    public ResponseEntity<RentResponse> createRent(@RequestBody RentRequest request) {
        RentResponse created = rentService.createRent(request);
        return ResponseEntity.created(URI.create(ApiEndpoints.BASE_URL + ApiEndpoints.GET_RENT_BY_ID
                .replace(ApiEndpoints.ID, created.id().toString()))).body(created);
    }

    @GetMapping(ApiEndpoints.GET_RENT_BY_ID)
    public ResponseEntity<RentResponse> getRent(@PathVariable Long id) {
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @PutMapping(ApiEndpoints.UPDATE_RENT)
    public ResponseEntity<RentResponse> updateRent(@PathVariable Long id, @RequestBody RentUpdateRequest request) {
        return ResponseEntity.ok(rentService.updateRent(id, request));
    }

    @DeleteMapping(ApiEndpoints.DELETE_RENT)
    public ResponseEntity<RentDtos.DeletedRentResponse> deleteRent(@PathVariable Long id) {
        return ResponseEntity.ok(rentService.deleteRent(id));
    }
}
