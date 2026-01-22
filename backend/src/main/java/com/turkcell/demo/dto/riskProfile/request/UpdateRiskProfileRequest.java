package com.turkcell.demo.dto.riskProfile.request;

import com.turkcell.demo.entity.RiskLevel;

import java.util.List;

public record UpdateRiskProfileRequest(
        Double riskScore,
        RiskLevel riskLevel,
        List<String> signals
) {
}
