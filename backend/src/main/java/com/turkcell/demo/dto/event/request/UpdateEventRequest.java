package com.turkcell.demo.dto.event.request;

import com.turkcell.demo.entity.EventType;

public record UpdateEventRequest(
        String userId,
        String service,
        EventType eventType,
        Double amount,
        String unit,
        String meta
) {
}
