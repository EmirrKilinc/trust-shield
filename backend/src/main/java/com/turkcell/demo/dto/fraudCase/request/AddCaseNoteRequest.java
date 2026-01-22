package com.turkcell.demo.dto.fraudCase.request;

public record AddCaseNoteRequest(
        String actor,
        String note
) {}