package com.turkcell.demo.dto.riskRule.response;

import com.turkcell.demo.entity.ActionType;

public record RiskRuleResponse(
        String id,
        String condition,
        ActionType action,
        Integer priority,
        Boolean active
) {
}
