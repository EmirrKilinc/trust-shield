package com.turkcell.demo.dsl.context;

import com.turkcell.demo.entity.EventType;

import java.util.Map;

public class EventContext {

    private final String userId;
    private final String service;
    private final EventType eventType;
    private final Double value;
    private final Map<String, String> meta;

    public EventContext(String userId,
                        String service,
                        EventType eventType,
                        Double value,
                        Map<String, String> meta) {
        this.userId = userId;
        this.service = service;
        this.eventType = eventType;
        this.value = value;
        this.meta = meta;
    }

    public String getUserId() { return userId; }
    public String getService() { return service; }
    public EventType getEventType() { return eventType; }
    public Double getValue() { return value; }
    public Map<String, String> getMeta() { return meta; }
}
