package com.turkcell.demo.controller;

import com.turkcell.demo.dto.fraudCase.request.AddCaseNoteRequest;
import com.turkcell.demo.dto.fraudCase.request.CloseFraudCaseRequest;
import com.turkcell.demo.dto.fraudCase.request.OpenFraudCaseRequest;
import com.turkcell.demo.dto.fraudCase.request.UpdateCaseStatusRequest;
import com.turkcell.demo.dto.fraudCase.response.CaseActionResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseDetailResponse;
import com.turkcell.demo.dto.fraudCase.response.FraudCaseResponse;
import com.turkcell.demo.service.FraudCaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fraud-cases")
public class FraudCaseController {

    private final FraudCaseService fraudCaseService;

    public FraudCaseController(FraudCaseService fraudCaseService) {
        this.fraudCaseService = fraudCaseService;
    }

    @GetMapping
    public ResponseEntity<List<FraudCaseResponse>> getAllCases() {
        return ResponseEntity.ok(fraudCaseService.getAllCases());
    }

    @GetMapping("/open")
    public ResponseEntity<List<FraudCaseResponse>> getOpenCases() {
        return ResponseEntity.ok(fraudCaseService.getOpenCases());
    }

    @GetMapping("/{id}/actions")
    public ResponseEntity<List<CaseActionResponse>> getCaseActions(@PathVariable Long id) {
        return ResponseEntity.ok(fraudCaseService.getCaseActions(id));
    }

    @PostMapping
    public ResponseEntity<FraudCaseDetailResponse> openCase(@RequestBody OpenFraudCaseRequest request) {
        return ResponseEntity.ok(fraudCaseService.openCase(request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<FraudCaseDetailResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateCaseStatusRequest request) {
        return ResponseEntity.ok(fraudCaseService.updateStatus(id, request));
    }

    @PutMapping("/{id}/notes")
    public ResponseEntity<CaseActionResponse> addNote(
            @PathVariable Long id,
            @RequestBody AddCaseNoteRequest request) {
        return ResponseEntity.ok(fraudCaseService.addNote(id, request));
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<FraudCaseDetailResponse> closeCase(@PathVariable Long id, @RequestBody CloseFraudCaseRequest request) {
        return ResponseEntity.ok(fraudCaseService.closeCase(id, request));
    }


}