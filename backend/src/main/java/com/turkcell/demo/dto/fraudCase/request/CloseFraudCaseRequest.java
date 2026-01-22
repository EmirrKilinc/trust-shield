package com.turkcell.demo.dto.fraudCase.request;

public record CloseFraudCaseRequest(
        String actor,
        String note
) {
}
