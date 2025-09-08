package com.rentwise.controller;

import com.rentwise.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send/{tenantId}")
    public ResponseEntity<String> sendNotification(@PathVariable Long tenantId) {
        notificationService.sendNotification(tenantId);
        return ResponseEntity.ok("Notification sent to tenant: " + tenantId);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<String>> getUpcomingNotifications() {
        return ResponseEntity.ok(notificationService.getUpcomingDueNotifications());
    }
}

