package com.turkcell.demo.service;


import com.turkcell.demo.dto.riskRule.mapper.RiskRuleMapper;
import com.turkcell.demo.dto.riskRule.request.CreateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.request.UpdateRiskRuleRequest;
import com.turkcell.demo.dto.riskRule.response.RiskRuleResponse;
import com.turkcell.demo.entity.RiskRule;
import com.turkcell.demo.exception.ErrorMessages;
import com.turkcell.demo.exception.RiskRuleNotFoundException;
import com.turkcell.demo.repository.RiskRuleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RiskRuleService {
    private final RiskRuleRepository riskRuleRepository;
    private final RiskRuleMapper riskRuleMapper;

    public RiskRuleService(RiskRuleRepository riskRuleRepository, RiskRuleMapper riskRuleMapper) {
        this.riskRuleRepository = riskRuleRepository;
        this.riskRuleMapper = riskRuleMapper;
    }


    public List<RiskRuleResponse> getAll() {
        return riskRuleRepository.findAll().stream()
                .map(riskRuleMapper::toRiskRuleResponseFromRiskRule)
                .collect(Collectors.toList());
    }
    public RiskRuleResponse createRiskRule(CreateRiskRuleRequest request){
        RiskRule riskRule = riskRuleMapper.createRiskRule(request);
        RiskRule savedRiskRule = riskRuleRepository.save(riskRule);
        return riskRuleMapper.toRiskRuleResponseFromRiskRule(savedRiskRule);
    }

    public RiskRuleResponse updateRiskRule(UpdateRiskRuleRequest request, Long id){
        RiskRule riskRule = riskRuleRepository.findById(id)
                .orElseThrow(() -> new RiskRuleNotFoundException(String.format(ErrorMessages.RISK_RULE_NOT_FOUND_WITH_ID, id)));

        riskRuleMapper.updateRiskRule(request, riskRule);
        RiskRule updatedRiskRule = riskRuleRepository.save(riskRule);
        return riskRuleMapper.toRiskRuleResponseFromRiskRule(updatedRiskRule);
    }

    public RiskRuleResponse getRiskRule(Long id){
        return riskRuleRepository.findById(id).map(riskRuleMapper::toRiskRuleResponseFromRiskRule)
                .orElseThrow(() -> new RiskRuleNotFoundException(String.format(ErrorMessages.RISK_RULE_NOT_FOUND_WITH_ID, id)));
    }

    public List<RiskRuleResponse> getAllActiveRiskRules() {
        return riskRuleRepository.findAllByIsActiveTrue().stream()
                .map(riskRuleMapper::toRiskRuleResponseFromRiskRule)
                .collect(Collectors.toList());
    }

    public RiskRuleResponse setActive(Long id, Boolean isActive) {
        RiskRule riskRule = riskRuleRepository.findById(id)
                .orElseThrow(() -> new RiskRuleNotFoundException(String.format(ErrorMessages.RISK_RULE_NOT_FOUND_WITH_ID, id)));

        riskRule.setActive(isActive);
        RiskRule updatedRiskRule = riskRuleRepository.save(riskRule);
        return riskRuleMapper.toRiskRuleResponseFromRiskRule(updatedRiskRule);
    }
}
