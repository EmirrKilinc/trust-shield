package com.turkcell.demo.dto.fraudCase.response;

import com.turkcell.demo.entity.CasePriority;
import com.turkcell.demo.entity.CaseStatus;

import java.time.LocalDateTime;

public record FraudCaseDetailResponse(
        String caseId,
        String userId,
        String openedBy,
        String caseType,
        CaseStatus status,
        CasePriority priority,
        LocalDateTime openedAt,
        java.util.List<CaseActionResponse> actions
) {
}
