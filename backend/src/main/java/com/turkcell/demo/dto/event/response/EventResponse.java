package com.turkcell.demo.dto.event.response;

import com.turkcell.demo.entity.EventType;

import java.time.LocalDateTime;

public record EventResponse(
        String id,
        String userId,
        String service,
        EventType eventType,
        Double amount,
        String unit,
        String meta,
        LocalDateTime timestamp
) {
}
