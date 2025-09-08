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
                "araibali000gmail.com",
                "Test Email from RentWise",
                "✅ Your email integration works!"
        );
        return "Email sent successfully!";
    }
}
