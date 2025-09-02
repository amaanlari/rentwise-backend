package com.rentwise.controller;

import com.rentwise.dto.RentDto;
import com.rentwise.mapper.RentMapper;
import com.rentwise.model.Rent;
import com.rentwise.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentDto> createRent(@RequestBody RentDto dto) {
        return ResponseEntity.ok(rentService.createRent(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDto> getRentById(@PathVariable String id) {
        return ResponseEntity.ok(rentService.getRentById(id));
    }

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDto> updateRent(@PathVariable String id, @RequestBody RentDto dto) {
        return ResponseEntity.ok(rentService.updateRent(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable String id) {
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }
}