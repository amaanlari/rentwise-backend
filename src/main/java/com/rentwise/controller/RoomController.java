package com.rentwise.controller;

import com.rentwise.dto.RoomDto;
import com.rentwise.mapper.RoomMapper;
import com.rentwise.model.Room;
import com.rentwise.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService service;
    @PostMapping public RoomDto create(@RequestBody RoomDto dto){ return service.createRoom(dto);}
    @GetMapping("/{id}") public RoomDto get(@PathVariable Long id){ return service.getRoom(id);}
    @GetMapping public List<RoomDto> getAll(){ return service.getAllRooms();}
}