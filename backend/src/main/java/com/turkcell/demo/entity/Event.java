package com.turkcell.demo.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event")
public class Event {
    //event_id, user_id, service, event_type, value, unit, meta, timestamp
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "service")
    private String service;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit")
    private String unit;

    @Column(name = "meta")
    private String meta;

    @Column(name = "event_timestamp")
    private LocalDateTime timestamp;

    public Event() {
    }

    public Event(String userId, String service, EventType eventType,
                 Double amount, String unit, String meta, LocalDateTime timestamp) {
        this.userId = userId;
        this.service = service;
        this.eventType = eventType;
        this.amount = amount;
        this.unit = unit;
        this.meta = meta;
        this.timestamp = timestamp;
    }
    public String getEventId() {
        return "EV-" + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
