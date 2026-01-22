package com.turkcell.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "risk_rule")
public class RiskRule {
    //rule_id, condition, action, priority, is_active
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_condition",columnDefinition = "TEXT")
    private String ruleCondition;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "is_active")
    private Boolean isActive;

    public RiskRule() {
    }

    public RiskRule(String condition, ActionType action,
                    Integer priority) {
        this.ruleCondition = condition;
        this.action = action;
        this.priority = priority;
        this.isActive = true;
    }
    public String getRuleId() {
        return "RR-" + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long ruleId) {
        this.id = ruleId;
    }

    public String getCondition() {
        return ruleCondition;
    }

    public void setCondition(String condition) {
        this.ruleCondition = condition;
    }

    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
