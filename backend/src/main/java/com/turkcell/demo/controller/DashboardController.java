package com.turkcell.demo.controller;

import com.turkcell.demo.dto.decision.response.DecisionResponse;
import com.turkcell.demo.dto.event.response.EventResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseResponse;
import com.turkcell.demo.dto.riskProfile.response.RiskProfileResponse;
import com.turkcell.demo.dto.riskRule.response.RiskRuleResponse;
import com.turkcell.demo.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController {

    private final EventService eventService;
    private final RiskProfileService riskProfileService;
    private final FraudCaseService fraudCaseService;
    private final RiskRuleService riskRuleService;
    private final DecisionService decisionService;

    public DashboardController(EventService eventService,
                               RiskProfileService riskProfileService,
                               FraudCaseService fraudCaseService,
                               RiskRuleService riskRuleService,
                               DecisionService decisionService) {
        this.eventService = eventService;
        this.riskProfileService = riskProfileService;
        this.fraudCaseService = fraudCaseService;
        this.riskRuleService = riskRuleService;
        this.decisionService = decisionService;
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();

        // Son eventler
        List<EventResponse> events = eventService.getAllEvents();
        summary.put("recentEvents", events);
        summary.put("totalEvents", events.size());

        // Risk profilleri
        List<RiskProfileResponse> profiles = riskProfileService.getAllRiskProfiles();
        summary.put("riskProfiles", profiles);

        // Açık fraud case'ler
        List<FraudCaseResponse> openCases = fraudCaseService.getOpenCases();
        summary.put("openCases", openCases);
        summary.put("totalOpenCases", openCases.size());

        // Aktif kurallar
        List<RiskRuleResponse> activeRules = riskRuleService.getAllActiveRiskRules();
        summary.put("activeRules", activeRules);
        summary.put("totalActiveRules", activeRules.size());

        // Son kararlar
        List<DecisionResponse> decisions = decisionService.getAll();
        summary.put("recentDecisions", decisions);
        summary.put("totalDecisions", decisions.size());

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventResponse>> getRecentEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/risk-profiles")
    public ResponseEntity<List<RiskProfileResponse>> getRiskProfiles() {
        return ResponseEntity.ok(riskProfileService.getAllRiskProfiles());
    }

    @GetMapping("/open-cases")
    public ResponseEntity<List<FraudCaseResponse>> getOpenCases() {
        return ResponseEntity.ok(fraudCaseService.getOpenCases());
    }

    @GetMapping("/active-rules")
    public ResponseEntity<List<RiskRuleResponse>> getActiveRules() {
        return ResponseEntity.ok(riskRuleService.getAllActiveRiskRules());
    }

    @GetMapping("/decisions")
    public ResponseEntity<List<DecisionResponse>> getDecisions() {
        return ResponseEntity.ok(decisionService.getAll());
    }
}