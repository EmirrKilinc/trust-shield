package com.turkcell.demo.dto.riskProfile.mapper;


import com.turkcell.demo.dto.riskProfile.request.CreateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.request.UpdateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.response.RiskProfileResponse;
import com.turkcell.demo.entity.RiskProfile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RiskProfileMapper {
    public RiskProfileResponse toRiskProfileResponseFromRiskProfile(RiskProfile riskProfile) {
        return new RiskProfileResponse(
                riskProfile.getUserId(),
                riskProfile.getRiskScore(),
                riskProfile.getRiskLevel(),
                riskProfile.getSignals());
    }

    public RiskProfile toRiskProfileFromCreateRequest(CreateRiskProfileRequest request) {
        return new RiskProfile();
    }

    public void updateRiskProfileFromRequest(UpdateRiskProfileRequest request, RiskProfile riskProfile){
        riskProfile.setRiskScore(request.riskScore());
        riskProfile.setRiskLevel(request.riskLevel());
        riskProfile.setSignals(request.signals());
    }
}