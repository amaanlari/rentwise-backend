package com.rentwise.service;

import java.util.List;

public interface NotificationService {
    void sendNotification(Long tenantId);
    List<String> getUpcomingDueNotifications();
}