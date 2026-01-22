package com.turkcell.demo.controller;

import com.turkcell.demo.dto.riskRule.request.CreateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.request.UpdateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.response.RiskRuleResponse;
import com.turkcell.demo.service.RiskRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/risk-rules")
public class RiskRuleController {

    private final RiskRuleService riskRuleService;

    public RiskRuleController(RiskRuleService riskRuleService) {
        this.riskRuleService = riskRuleService;
    }

    @GetMapping
    public ResponseEntity<List<RiskRuleResponse>> getAllRiskRules() {
        return ResponseEntity.ok(riskRuleService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<RiskRuleResponse>> getAllActiveRiskRules() {
        return ResponseEntity.ok(riskRuleService.getAllActiveRiskRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiskRuleResponse> getRiskRuleById(@PathVariable Long id) {
        return ResponseEntity.ok(riskRuleService.getRiskRule(id));
    }

    @PostMapping
    public ResponseEntity<RiskRuleResponse> createRiskRule(@RequestBody CreateRiskRuleRequest request) {
        return ResponseEntity.ok(riskRuleService.createRiskRule(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RiskRuleResponse> updateRiskRule(
            @PathVariable Long id,
            @RequestBody UpdateRiskRuleRequest request) {
        return ResponseEntity.ok(riskRuleService.updateRiskRule(request, id));
    }

    @PutMapping("/{id}/{active}/activate")
    public ResponseEntity<RiskRuleResponse> activateRiskRule(@PathVariable Long id, @PathVariable Boolean active) {
        return ResponseEntity.ok(riskRuleService.setActive(id, active));
    }
}