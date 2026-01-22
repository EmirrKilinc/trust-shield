package com.turkcell.demo.dto.riskRule.request;

import com.turkcell.demo.entity.ActionType;

public record CreateRiskRuleRequest(
        String condition,
        ActionType action,
        Integer priority
) {
}
