package com.turkcell.demo.dto.riskRule.request;

import com.turkcell.demo.entity.ActionType;

public record UpdateRiskRuleRequest(
        String condition,
        ActionType action,
        Integer priority
){
}
