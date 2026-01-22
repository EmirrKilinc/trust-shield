package com.turkcell.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "risk_profile")
public class RiskProfile {
    //user_id, risk_score, risk_level, signals
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(name = "risk_score")
    private Double riskScore;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> signals;

    public RiskProfile() {
        this.riskScore = 0.0;
        this.riskLevel = RiskLevel.LOW;
        this.signals = new ArrayList<>();
    }
    public String getUserId() {
        return userId;
    }
    public Long getId() {
        return id;
    }


    public void setId(Long userId) {
        this.id = userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public List<String> getSignals() {
        return signals;
    }

    public void setSignals(List<String> signals) {
        this.signals = signals;
    }
}
