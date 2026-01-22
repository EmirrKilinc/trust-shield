package com.turkcell.demo.dto.riskProfile.response;

import com.turkcell.demo.entity.RiskLevel;

import java.util.List;

public record RiskProfileResponse(
        String userId,
        Double riskScore,
        RiskLevel riskLevel,
        List<String> signals
) {
}
