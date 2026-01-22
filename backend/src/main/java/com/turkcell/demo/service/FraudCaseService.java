package com.turkcell.demo.service;

import com.turkcell.demo.dto.fraudCase.mapper.FraudCaseMapper;
import com.turkcell.demo.dto.fraudCase.request.*;
import com.turkcell.demo.dto.fraudCase.response.CaseActionResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseDetailResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseResponse;
import com.turkcell.demo.entity.*;
import com.turkcell.demo.exception.ErrorMessages;
import com.turkcell.demo.exception.FraudCaseNotFoundException;
import com.turkcell.demo.repository.CaseActionRepository;
import com.turkcell.demo.repository.FraudCaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FraudCaseService {

    private final FraudCaseRepository fraudCaseRepository;
    private final CaseActionRepository caseActionRepository;
    private final FraudCaseMapper mapper;

    public FraudCaseService(FraudCaseRepository fraudCaseRepository,
                            CaseActionRepository caseActionRepository,
                            FraudCaseMapper mapper) {
        this.fraudCaseRepository = fraudCaseRepository;
        this.caseActionRepository = caseActionRepository;
        this.mapper = mapper;
    }

    @Transactional
    public FraudCaseDetailResponse openCase(OpenFraudCaseRequest request) {
        FraudCase fraudCase = mapper.toEntity(request);
        FraudCase saved = fraudCaseRepository.save(fraudCase);


        AddCaseActionRequest actionRequest = new AddCaseActionRequest(
                saved.getId(),
                CaseActionType.CASE_OPENED,
                "SYSTEM",
                request.note()
        );
        addAction(actionRequest);

        return mapper.toResponse(saved);
    }


    @Transactional
    public CaseActionResponse addAction(AddCaseActionRequest request) {
        FraudCase fraudCase = fraudCaseRepository.findById(request.caseId())
                .orElseThrow(() -> new FraudCaseNotFoundException(String.format(ErrorMessages.FRAUD_CASE_NOT_FOUND_WITH_ID, request.caseId())));

        CaseAction action = mapper.toActionEntity(request, fraudCase);
        CaseAction saved = caseActionRepository.save(action);

        return mapper.toActionResponse(saved);
    }


    public List<FraudCaseResponse> getAllCases() {
        return fraudCaseRepository.findAll().stream()
                .map(mapper::toResponseWithoutActions)
                .toList();
    }


    public List<FraudCaseResponse> getOpenCases() {
        return fraudCaseRepository.findByStatus(CaseStatus.OPEN).stream()
                .map(mapper::toResponseWithoutActions)
                .toList();
    }


    @Transactional
    public FraudCaseDetailResponse updateStatus(Long caseId, UpdateCaseStatusRequest request) {
        FraudCase fraudCase = fraudCaseRepository.findById(caseId)
                .orElseThrow(() -> new FraudCaseNotFoundException(String.format(ErrorMessages.FRAUD_CASE_NOT_FOUND_WITH_ID, caseId)));

        CaseStatus oldStatus = fraudCase.getStatus();
        fraudCase.setStatus(request.status());
        FraudCase saved = fraudCaseRepository.save(fraudCase);


        String note = String.format("Status: %s → %s. %s", oldStatus, request.status(), request.note());
        AddCaseActionRequest actionRequest = new AddCaseActionRequest(
                caseId,
                CaseActionType.STATUS_CHANGE,
                request.actor(),
                note
        );
        addAction(actionRequest);

        return mapper.toResponse(saved);
    }


    @Transactional
    public CaseActionResponse addNote(Long caseId, AddCaseNoteRequest request) {
        AddCaseActionRequest actionRequest = new AddCaseActionRequest(
                caseId,
                CaseActionType.NOTE_ADDED,
                request.actor(),
                request.note()
        );
        return addAction(actionRequest);
    }


    @Transactional
    public FraudCaseDetailResponse closeCase(Long caseId, CloseFraudCaseRequest closeRequest) {
        UpdateCaseStatusRequest request = new UpdateCaseStatusRequest(
                CaseStatus.CLOSED,
                closeRequest.actor(),
                closeRequest.note()
        );
        return updateStatus(caseId, request);
    }


    public List<CaseActionResponse> getCaseActions(Long caseId) {
        return caseActionRepository.findActionsByCaseId(caseId).stream()
                .map(mapper::toActionResponse)
                .toList();
    }
}