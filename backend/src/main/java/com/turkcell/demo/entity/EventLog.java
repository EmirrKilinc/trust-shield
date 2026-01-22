package com.turkcell.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "eventLog")
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String eventId; // EV-5002
    private String userId;
    private String service; // Paycell, BiP
    private String eventType; // PAYMENT, LOGIN
    private Double amount;
    private String unit; // TRY

    @Column(columnDefinition = "TEXT")
    private String meta; // JSON veya Key-Value string: "merchant=Crypto"

    @Column(name = "event_timestamp")
    private LocalDateTime timestamp;

    public EventLog() {
    }

    public EventLog(String eventId, String userId, String service,
                    String eventType, Double amount, String unit,
                    String meta, LocalDateTime timestamp) {
        this.eventId = eventId;
        this.userId = userId;
        this.service = service;
        this.eventType = eventType;
        this.amount = amount;
        this.unit = unit;
        this.meta = meta;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
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