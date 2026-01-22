package com.turkcell.demo.dto.fraudCase.response;

import com.turkcell.demo.entity.CaseActionType;
import java.time.LocalDateTime;

public record CaseActionResponse(
        String actionId,
        CaseActionType actionType,
        String actor,
        String note,
        LocalDateTime timestamp
) {}