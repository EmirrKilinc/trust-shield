package com.turkcell.demo.service;

import com.turkcell.demo.dsl.RuleEvaluator;
import com.turkcell.demo.dsl.context.EventContext;
import com.turkcell.demo.dto.bipnotification.request.BipNotificationRequest;
import com.turkcell.demo.dto.decision.request.CreateDecisionRequest;
import com.turkcell.demo.dto.fraudCase.request.OpenFraudCaseRequest;
import com.turkcell.demo.dto.riskRule.response.RiskRuleResponse;
import com.turkcell.demo.entity.ActionType;
import com.turkcell.demo.entity.CasePriority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.turkcell.demo.entity.ActionType.*;

@Service
public class RuleEngineService {
    private final RiskRuleService riskRuleService;
    private final DecisionService decisionService;
    private final RuleEvaluator ruleEvaluator;
    private final BipNotificationService bipNotificationService;
    private final FraudCaseService fraudCaseService;

    public RuleEngineService(RiskRuleService riskRuleService,
                             DecisionService decisionService,
                             RuleEvaluator ruleEvaluator, BipNotificationService bipNotificationService, FraudCaseService fraudCaseService) {
        this.riskRuleService = riskRuleService;
        this.decisionService = decisionService;
        this.ruleEvaluator = ruleEvaluator;
        this.bipNotificationService = bipNotificationService;
        this.fraudCaseService = fraudCaseService;
    }


    public void evaluateRules(EventContext context) {

        if (context == null) return;

        List<RiskRuleResponse> rules = riskRuleService.getAllActiveRiskRules();
        if (rules == null || rules.isEmpty()) {
            return;
        }
        List<RiskRuleResponse> matchedRules = new ArrayList<>();

        for (RiskRuleResponse rule : rules) {
            if (ruleEvaluator.evaluate(rule.condition(), context)) {
                matchedRules.add(rule);
            }
        }

        if (matchedRules.isEmpty()) {

            CreateDecisionRequest request = new CreateDecisionRequest(
                    context.getUserId(),
                    List.of(),
                    NO_ACTION,
                    List.of()
            );
            decisionService.createDecision(request);
            return;
        }

        matchedRules.sort(Comparator.comparingInt(RiskRuleResponse::priority));

        RiskRuleResponse selectedRule = matchedRules.get(0);

        List<ActionType> suppressedActions = matchedRules.stream()
                .skip(1)
                .map(RiskRuleResponse::action)
                .distinct()
                .toList();



        CreateDecisionRequest request = new CreateDecisionRequest(
                context.getUserId(),
                matchedRules.stream().map(RiskRuleResponse::id).toList(),
                selectedRule.action(),
                suppressedActions
        );

        decisionService.createDecision(request);

        executeAction(selectedRule.action(), context);
    }

    private void executeAction(ActionType action, EventContext context) {
        String userId = context.getUserId();

        switch (action) {
            case FORCE_2FA -> {
                BipNotificationRequest request = new BipNotificationRequest(
                        userId,
                        context.getService(),
                        "Güvenlik nedeniyle ek doğrulama (2FA) zorunlu hale getirildi."
                );
                bipNotificationService.sendBipNotification(request);
            }

            case PAYMENT_REVIEW ->{
                BipNotificationRequest request = new BipNotificationRequest(
                        userId,
                        context.getService(),
                        "İşleminiz incelemeye alındı."
                );
                bipNotificationService.sendBipNotification(request);
            }

            case TEMPORARY_BLOCK -> {
                BipNotificationRequest request = new BipNotificationRequest(
                        userId,
                        context.getService(),
                        "Hesabınız geçici olarak bloke edildi."
                );
                bipNotificationService.sendBipNotification(request);
            }

            case OPEN_FRAUD_CASE -> {
                BipNotificationRequest request = new BipNotificationRequest(
                        userId,
                        context.getService(),
                        "İnceleme başlatıldı."
                );
                OpenFraudCaseRequest fraudCaseRequest = new OpenFraudCaseRequest(
                        context.getUserId(),
                        "SUSPICIOUS_ACTIVITY",
                        CasePriority.HIGH,
                        "Fraud case opened automatically due to suspicious activity"
                );
                fraudCaseService.openCase(fraudCaseRequest);
                bipNotificationService.sendBipNotification(request);
            }

            case ANOMALY_ALERT -> {
                BipNotificationRequest request = new BipNotificationRequest(
                        userId,
                        context.getService(),
                        "Anormal kullanım tespit edildi."
                );
                bipNotificationService.sendBipNotification(request);
            }

            case NO_ACTION -> { }
        }
    }
}
