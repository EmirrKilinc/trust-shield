package com.turkcell.demo.dto.bipnotification.request;

public record BipNotificationRequest(
        String id,
        String service,
        String message
) {
}
