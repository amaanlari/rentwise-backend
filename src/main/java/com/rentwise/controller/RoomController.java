package com.rentwise.controller;

import com.rentwise.constants.ApiEndpoints;
import com.rentwise.dto.RoomDtos.DeletedRoomResponse;
import com.rentwise.dto.RoomDtos.RoomRequest;
import com.rentwise.dto.RoomDtos.RoomResponse;
import com.rentwise.dto.RoomDtos.RoomUpdateRequest;
import com.rentwise.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.BASE_URL)
public class RoomController {

    private final RoomService roomService;

    @PostMapping(ApiEndpoints.CREATE_ROOM)
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        RoomResponse created = roomService.createRoom(request);
        return ResponseEntity.created(URI.create(ApiEndpoints.BASE_URL + ApiEndpoints.GET_ROOM_BY_ID
                .replace(ApiEndpoints.ID, created.id().toString()))).body(created);
    }

    @GetMapping(ApiEndpoints.GET_ROOM_BY_ID)
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PutMapping(ApiEndpoints.UPDATE_ROOM)
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @Valid @RequestBody RoomUpdateRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @DeleteMapping(ApiEndpoints.DELETE_ROOM)
    public ResponseEntity<DeletedRoomResponse> deleteRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.deleteRoom(id));
    }
}