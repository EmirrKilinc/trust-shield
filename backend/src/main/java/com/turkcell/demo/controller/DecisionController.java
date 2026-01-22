package com.turkcell.demo.controller;

import com.turkcell.demo.dto.decision.response.DecisionResponse;
import com.turkcell.demo.service.DecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/decisions")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(DecisionService decisionService) {
        this.decisionService = decisionService;
    }

    @GetMapping
    public ResponseEntity<List<DecisionResponse>> getAllDecisions() {
        return ResponseEntity.ok(decisionService.getAll());
    }
}