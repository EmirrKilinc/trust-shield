package com.turkcell.demo.dto.fraudCase.request;

import com.turkcell.demo.entity.CaseActionType;

public record AddCaseActionRequest(
        Long caseId,
        CaseActionType actionType,
        String actor,
        String note
) {}