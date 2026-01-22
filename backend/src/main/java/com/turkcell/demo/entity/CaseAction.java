package com.turkcell.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "case_actions")
public class CaseAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_id", nullable = false)
    private FraudCase fraudCase;

    private CaseActionType actionType;

    private String actor;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Column(name = "event_timestamp")
    private LocalDateTime timestamp;

    public CaseAction() {}

    public CaseAction(FraudCase fraudCase, CaseActionType actionType, String actor, String note) {
        this.fraudCase = fraudCase;
        this.actionType = actionType;
        this.actor = actor;
        this.note = note;
        this.timestamp = LocalDateTime.now();
    }
    public String getActionId() {
        return "CA-" + id;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public FraudCase getFraudCase() { return fraudCase; }
    public void setFraudCase(FraudCase fraudCase) { this.fraudCase = fraudCase; }

    public CaseActionType getActionType() { return actionType; }
    public void setActionType(CaseActionType actionType) { this.actionType = actionType; }

    public String getActor() { return actor; }
    public void setActor(String actor) { this.actor = actor; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}