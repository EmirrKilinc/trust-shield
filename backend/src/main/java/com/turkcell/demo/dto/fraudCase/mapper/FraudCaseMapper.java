package com.turkcell.demo.dto.fraudCase.mapper;

import com.turkcell.demo.dto.fraudCase.request.AddCaseActionRequest;
import com.turkcell.demo.dto.fraudCase.request.OpenFraudCaseRequest;
import com.turkcell.demo.dto.fraudCase.response.CaseActionResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseDetailResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseResponse;
import com.turkcell.demo.entity.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class FraudCaseMapper {

    public FraudCase toEntity(OpenFraudCaseRequest request) {
        FraudCase fraudCase = new FraudCase();
        fraudCase.setUserId(request.userId());
        fraudCase.setOpenedBy("SYSTEM");
        fraudCase.setCaseType(request.caseType());
        fraudCase.setStatus(CaseStatus.OPEN);
        fraudCase.setPriority(request.priority());
        fraudCase.setOpenedAt(LocalDateTime.now());
        return fraudCase;
    }

    public CaseAction toActionEntity(AddCaseActionRequest request, FraudCase fraudCase) {
        CaseAction action = new CaseAction();
        action.setFraudCase(fraudCase);
        action.setActionType(request.actionType());
        action.setActor(request.actor());
        action.setNote(request.note());
        action.setTimestamp(LocalDateTime.now());
        return action;
    }

    public FraudCaseDetailResponse toResponse(FraudCase fraudCase) {
        List<CaseActionResponse> actions = List.of();  // Default boş liste

        if (fraudCase.getHistory() != null) {
            actions = fraudCase.getHistory().stream()
                    .map(this::toActionResponse)
                    .toList();
        }

        return new FraudCaseDetailResponse(
                fraudCase.getCaseId(),
                fraudCase.getUserId(),
                fraudCase.getOpenedBy(),
                fraudCase.getCaseType(),
                fraudCase.getStatus(),
                fraudCase.getPriority(),
                fraudCase.getOpenedAt(),
                actions
        );
    }

    public FraudCaseResponse toResponseWithoutActions(FraudCase fraudCase) {
        return new FraudCaseResponse(
                fraudCase.getCaseId(),
                fraudCase.getUserId(),
                fraudCase.getOpenedBy(),
                fraudCase.getCaseType(),
                fraudCase.getStatus(),
                fraudCase.getPriority(),
                fraudCase.getOpenedAt()
        );
    }

    public CaseActionResponse toActionResponse(CaseAction action) {
        return new CaseActionResponse(
                action.getActionId(),
                action.getActionType(),
                action.getActor(),
                action.getNote(),
                action.getTimestamp()
        );
    }
}