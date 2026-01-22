package com.turkcell.demo.dto.fraudCase.request;

import com.turkcell.demo.entity.CaseStatus;

public record UpdateCaseStatusRequest(
        CaseStatus status,
        String actor,
        String note
) {}