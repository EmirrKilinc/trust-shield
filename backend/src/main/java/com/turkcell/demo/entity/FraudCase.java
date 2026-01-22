package com.turkcell.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fraud_case")
public class FraudCase {
    //case_id, user_id, opened_by, case_type, status, opened_at, priority

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "opened_by")
    private String openedBy;

    @Column(name = "case_type")
    private String caseType;

    @Enumerated(EnumType.STRING)
    private CaseStatus status;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @Enumerated(EnumType.STRING)
    private CasePriority priority;

    @OneToMany(mappedBy = "fraudCase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CaseAction> history = new ArrayList<>();  // ← Default boş liste

    public List<CaseAction> getHistory() {
        if (history == null) {
            history = new ArrayList<>();
        }
        return history;
    }

    public FraudCase() {
    }

    public FraudCase(String userId, String openedBy,
                     String caseType, CaseStatus status,
                     LocalDateTime openedAt, CasePriority priority,
                     List<CaseAction> history) {
        this.userId = userId;
        this.openedBy = openedBy;
        this.caseType = caseType;
        this.status = status;
        this.openedAt = openedAt;
        this.priority = priority;
        this.history = history;
    }
    public String getCaseId() {
        return "FC-" + id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public CaseStatus getStatus() {
        return status;
    }

    public void setStatus(CaseStatus status) {
        this.status = status;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public CasePriority getPriority() {
        return priority;
    }

    public void setPriority(CasePriority priority) {
        this.priority = priority;
    }

    public void setHistory(List<CaseAction> history) {
        this.history = history;
    }
}
