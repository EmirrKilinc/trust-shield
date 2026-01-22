package com.turkcell.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "decision")
public class Decision {
    //decision_id, user_id, triggered_rules, selected_action, suppressed_actions, timestamp
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @ElementCollection
    private List<String> triggeredRules;

    @Enumerated(EnumType.STRING)
    private ActionType selectedAction;

    @ElementCollection
    private List<ActionType> suppressedActions;

    @Column(name = "decision_timestamp")
    private LocalDateTime timestamp;

    public Decision() {
    }

    public Decision(String userId, List<String> triggeredRules, ActionType selectedAction, List<ActionType> suppressedActions, LocalDateTime timestamp) {
        this.userId = userId;
        this.triggeredRules = triggeredRules;
        this.selectedAction = selectedAction;
        this.suppressedActions = suppressedActions;
        this.timestamp = timestamp;
    }
    public String getDecisionId() {
        return "D-" + id;
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

    public List<String> getTriggeredRules() {
        return triggeredRules;
    }

    public void setTriggeredRules(List<String> triggeredRules) {
        this.triggeredRules = triggeredRules;
    }

    public ActionType getSelectedAction() {
        return selectedAction;
    }

    public void setSelectedAction(ActionType selectedAction) {
        this.selectedAction = selectedAction;
    }

    public List<ActionType> getSuppressedActions() {
        return suppressedActions;
    }

    public void setSuppressedActions(List<ActionType> suppressedActions) {
        this.suppressedActions = suppressedActions;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
