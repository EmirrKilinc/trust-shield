package com.turkcell.demo.dto.decision.mapper;

import com.turkcell.demo.dto.decision.request.CreateDecisionRequest;
import com.turkcell.demo.dto.decision.response.DecisionResponse;
import com.turkcell.demo.entity.Decision;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DecisionMapper {
    public Decision toEntity(CreateDecisionRequest request){
       return new Decision(
            request.userId(),
            request.triggeredRules(),
            request.selectedAction(),
            request.suppressedActions(),
            LocalDateTime.now()
       );
    }

    public DecisionResponse toDto(Decision decision){
        return new DecisionResponse(
                decision.getDecisionId(),
                decision.getUserId(),
                decision.getTriggeredRules(),
                decision.getSelectedAction().name(),
                decision.getSuppressedActions().stream().map(Enum::name).toList(),
                decision.getTimestamp().toString()
        );
    }
}
