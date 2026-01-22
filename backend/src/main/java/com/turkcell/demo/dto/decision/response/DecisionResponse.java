package com.turkcell.demo.dto.decision.response;

public record DecisionResponse(
        String decisionId,
        String userId,
        java.util.List<String> triggeredRules,
        String selectedAction,
        java.util.List<String> suppressedActions,
        String timestamp
) {
}
