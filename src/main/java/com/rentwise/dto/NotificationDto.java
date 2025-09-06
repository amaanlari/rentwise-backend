package com.rentwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Long id;
    private String recipientType;
    private String recipientEmail;
    private String message;
    private boolean sent;
    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;
    private Long rentId;
}