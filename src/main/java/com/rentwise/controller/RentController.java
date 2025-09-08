package com.rentwise.controller;

import com.rentwise.dto.RentDto;
import com.rentwise.service.RentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rents")
@RequiredArgsConstructor
public class RentController {

    private final RentService rentService;

    @PostMapping
    public ResponseEntity<RentDto> createRent(@RequestBody RentDto rentDto) {
        return ResponseEntity.ok(rentService.createRent(rentDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentDto> getRent(@PathVariable Long id) {
        return ResponseEntity.ok(rentService.getRent(id));
    }

    @GetMapping
    public ResponseEntity<List<RentDto>> getAllRents() {
        return ResponseEntity.ok(rentService.getAllRents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentDto> updateRent(@PathVariable Long id, @RequestBody RentDto rentDto) {
        return ResponseEntity.ok(rentService.updateRent(id, rentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
        return ResponseEntity.noContent().build();
    }
}