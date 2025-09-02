package com.rentwise.controller;

import com.rentwise.dto.OwnerDto;
import com.rentwise.mapper.OwnerMapper;
import com.rentwise.model.Owner;
import com.rentwise.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto dto) {
        return ResponseEntity.ok(ownerService.createOwner(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable String id) {
        return ResponseEntity.ok(ownerService.getOwnerById(id));
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable String id, @RequestBody OwnerDto dto) {
        return ResponseEntity.ok(ownerService.updateOwner(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable String id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
