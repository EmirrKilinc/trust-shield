package com.turkcell.demo.service;

import com.turkcell.demo.dto.decision.mapper.DecisionMapper;
import com.turkcell.demo.dto.decision.request.CreateDecisionRequest;
import com.turkcell.demo.dto.decision.response.DecisionResponse;
import com.turkcell.demo.entity.Decision;
import com.turkcell.demo.repository.DecisionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisionService {

    private final DecisionRepository decisionRepository;
    private final DecisionMapper decisionMapper;

    public DecisionService(DecisionRepository decisionRepository, DecisionMapper decisionMapper) {
        this.decisionRepository = decisionRepository;
        this.decisionMapper = decisionMapper;
    }
    @Transactional
    public DecisionResponse createDecision(CreateDecisionRequest request) {
        Decision decision = decisionMapper.toEntity(request);
        Decision savedDecision = decisionRepository.save(decision);
        return decisionMapper.toDto(savedDecision);
    }

    public List<DecisionResponse> getAll(){
        return decisionRepository.findAll().stream()
                .map(decisionMapper::toDto)
                .toList();
    }
}
