package com.turkcell.demo.dto.riskRule.mapper;


import com.turkcell.demo.dto.riskRule.request.CreateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.request.UpdateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.response.RiskRuleResponse;
import com.turkcell.demo.entity.RiskRule;
import org.springframework.stereotype.Component;

@Component
public class RiskRuleMapper {

    public RiskRule createRiskRule(CreateRiskRuleRequest request) {

        return new RiskRule(
        request.condition(),
        request.action(),
        request.priority());


    }

    public void updateRiskRule(UpdateRiskRuleRequest request, RiskRule riskRule){
        riskRule.setPriority(request.priority());
        riskRule.setAction(request.action());
        riskRule.setCondition(request.condition());
    }


    public RiskRuleResponse toRiskRuleResponseFromRiskRule(RiskRule riskRule) {
        return new RiskRuleResponse(
                riskRule.getRuleId(),
                riskRule.getCondition(),
                riskRule.getAction(),
                riskRule.getPriority(),
                riskRule.getActive());

    }
}