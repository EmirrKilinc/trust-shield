package com.turkcell.demo.dto.fraudCase.request;

import com.turkcell.demo.entity.CasePriority;

public record OpenFraudCaseRequest(
        String userId,
        String caseType,
        CasePriority priority,
        String note
) {}