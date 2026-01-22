package com.turkcell.demo.dto.bipnotification.response;

public record BipNotificationResponse(
        String notificationId,
        String recipient,
        String message,
        String status,
        String timestamp
) {
}
