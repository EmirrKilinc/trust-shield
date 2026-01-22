package com.turkcell.demo.dto.fraudCase.response;

import com.turkcell.demo.entity.CasePriority;
import com.turkcell.demo.entity.CaseStatus;
import java.time.LocalDateTime;
import java.util.List;

public record FraudCaseResponse(
        String caseId,
        String userId,
        String openedBy,
        String caseType,
        CaseStatus status,
        CasePriority priority,
        LocalDateTime openedAt
) {}