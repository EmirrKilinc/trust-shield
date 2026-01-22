package com.turkcell.demo.dto.decision.request;

import com.turkcell.demo.entity.ActionType;

import java.util.List;

public record CreateDecisionRequest(
        String userId,
        List<String> triggeredRules,
        ActionType selectedAction,
        List<ActionType> suppressedActions
) {
}
