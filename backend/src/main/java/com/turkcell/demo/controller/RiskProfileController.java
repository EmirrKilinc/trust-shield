package com.turkcell.demo.controller;

import com.turkcell.demo.dto.riskProfile.request.CreateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.request.UpdateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.response.RiskProfileResponse;
import com.turkcell.demo.service.RiskProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/risk-profiles")
public class RiskProfileController {

    private final RiskProfileService riskProfileService;

    public RiskProfileController(RiskProfileService riskProfileService) {
        this.riskProfileService = riskProfileService;
    }

    @GetMapping
    public ResponseEntity<List<RiskProfileResponse>> getAllRiskProfiles() {
        return ResponseEntity.ok(riskProfileService.getAllRiskProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskProfileResponse> getRiskProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(riskProfileService.getRiskProfile(id));
    }

    @PostMapping
    public ResponseEntity<RiskProfileResponse> createRiskProfile(@RequestBody CreateRiskProfileRequest request) {
        return ResponseEntity.ok(riskProfileService.createRiskProfile(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskProfileResponse> updateRiskProfile(
            @PathVariable Long id,
            @RequestBody UpdateRiskProfileRequest request) {
        return ResponseEntity.ok(riskProfileService.updateRiskProfile(request, id));
    }
}