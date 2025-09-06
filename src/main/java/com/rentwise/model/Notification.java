package com.rentwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientType; // TENANT / OWNER
    private String recipientEmail;
    private String message;
    private boolean sent;

    private LocalDateTime scheduledAt;
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;
}
