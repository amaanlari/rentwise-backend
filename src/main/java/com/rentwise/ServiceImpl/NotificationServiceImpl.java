package com.rentwise.ServiceImpl;

import com.rentwise.model.*;
import com.rentwise.repository.RentRepository;
import com.rentwise.service.EmailService;
import com.rentwise.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RentRepository rentRepository;
    private final EmailService emailService;

    @Override
    public void sendNotification(Long tenantId) {
        // In a real flow: fetch tenant email from Tenant entity
        // For now: simulate with a fixed email or query DB
        String tenantEmail = "tenant@example.com";
        emailService.sendEmail(
                tenantEmail,
                "Rent Notification",
                "Dear tenant, your rent is due soon or overdue. Please check your account."
        );
    }

    @Override
    public List<String> getUpcomingDueNotifications() {
        LocalDate now = LocalDate.now();
        LocalDate upcoming = now.plusDays(2);

        List<Rent> rents = rentRepository.findAll();

        List<String> notifications = rents.stream()
                .filter(r -> r.getDueDate() != null && r.getStatus() != RentStatus.PAID)
                .map(r -> {
                    if (r.getDueDate().isBefore(now)) {
                        return "⚠️ OVERDUE: Rent for Room " + r.getRoom().getRoomNumber()
                                + " was due on " + r.getDueDate();
                    } else if (r.getDueDate().isEqual(now) || r.getDueDate().isEqual(upcoming)) {
                        return "⏰ Reminder: Rent for Room " + r.getRoom().getRoomNumber()
                                + " is due on " + r.getDueDate();
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // ✅ Send emails to tenants
        for (Rent rent : rents) {
            Room room = rent.getRoom();

            if (room != null && room.getTenants() != null) {
                for (Tenant tenant : room.getTenants()) {
                    // Only active primary tenant
                    if (tenant.getExitDate() == null
                            && tenant.getTenantType() == TenantType.PRIMARY
                            && tenant.getEmail() != null) {

                        for (String note : notifications) {
                            emailService.sendEmail(
                                    tenant.getEmail(),
                                    "Rent Reminder",
                                    note
                            );
                        }
                    }
                }
            }
        }
        return notifications;
    }
}
