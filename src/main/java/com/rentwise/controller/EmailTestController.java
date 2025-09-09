package com.rentwise.controller;

import com.rentwise.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test-email")
public class EmailTestController {

    private final EmailService emailService;

    @GetMapping
    public String sendTestEmail() {
        emailService.sendEmail(
                "ashfaka480@gmail.com",
                "\"⏰ Reminder: Rent for Room:101",
                "\"⏰ Reminder: Rent for Room 101 is due on 09-09-2025"
        );
        return "Email sent successfully!";
    }
}
