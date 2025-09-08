package com.rentwise.scheduler;

import com.rentwise.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    // Every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendDailyNotifications() {
        System.out.println("🔔 Running daily rent notification job...");
        notificationService.getUpcomingDueNotifications(); // emails sent inside
    }
}
