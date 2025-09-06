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
    private final RentService service;
    @PostMapping public RentDto create(@RequestBody RentDto dto){ return service.createRent(dto);}
    @GetMapping("/{id}") public RentDto get(@PathVariable Long id){ return service.getRent(id);}
    @GetMapping public List<RentDto> getAll(){ return service.getAllRents();}
}