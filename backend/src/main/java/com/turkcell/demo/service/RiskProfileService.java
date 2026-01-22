package com.turkcell.demo.service;

import com.turkcell.demo.dsl.context.EventContext;
import com.turkcell.demo.dto.riskProfile.mapper.RiskProfileMapper;
import com.turkcell.demo.dto.riskProfile.request.CreateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.request.UpdateRiskProfileRequest;
import com.turkcell.demo.dto.riskProfile.response.RiskProfileResponse;
import com.turkcell.demo.entity.RiskLevel;
import com.turkcell.demo.entity.RiskProfile;
import com.turkcell.demo.exception.ErrorMessages;
import com.turkcell.demo.exception.RiskProfileNotFoundException;
import com.turkcell.demo.repository.RiskProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RiskProfileService {

    private final RiskProfileRepository riskProfileRepository;
    private final RiskProfileMapper riskProfileMapper;

    public RiskProfileService(RiskProfileRepository riskProfileRepository,
                              RiskProfileMapper riskProfileMapper) {
        this.riskProfileRepository = riskProfileRepository;
        this.riskProfileMapper = riskProfileMapper;
    }

    public List<RiskProfileResponse> getAllRiskProfiles() {
        return riskProfileRepository.findAll().stream()
                .map(riskProfileMapper::toRiskProfileResponseFromRiskProfile)
                .toList();
    }

    public RiskProfileResponse getRiskProfile(Long userId) {
        return riskProfileRepository.findById(userId)
                .map(riskProfileMapper::toRiskProfileResponseFromRiskProfile)
                .orElseThrow(() -> new RiskProfileNotFoundException(String.format(ErrorMessages.RISK_PROFILE_NOT_FOUND_WITH_ID, userId)));
    }

    public RiskProfileResponse createRiskProfile(CreateRiskProfileRequest request) {
        RiskProfile riskProfile = riskProfileMapper.toRiskProfileFromCreateRequest(request);
        RiskProfile savedRiskProfile = riskProfileRepository.save(riskProfile);
        return riskProfileMapper.toRiskProfileResponseFromRiskProfile(savedRiskProfile);
    }

    public RiskProfileResponse updateRiskProfile(UpdateRiskProfileRequest request, Long userId) {
        RiskProfile riskProfile = riskProfileRepository.findById(userId)
                .orElseThrow(() -> new RiskProfileNotFoundException(String.format(ErrorMessages.RISK_PROFILE_NOT_FOUND_WITH_ID, userId)));

        riskProfileMapper.updateRiskProfileFromRequest(request, riskProfile);
        RiskProfile updatedRiskProfile = riskProfileRepository.save(riskProfile);
        return riskProfileMapper.toRiskProfileResponseFromRiskProfile(updatedRiskProfile);
    }


    @Transactional
    public RiskProfileResponse updateProfileForEvent(EventContext context) {
        RiskProfile profile = getOrCreateProfile(context.getUserId());

        double additionalScore = calculateScore(context);
        profile.setRiskScore(profile.getRiskScore() + additionalScore);


        List<String> newSignals = detectSignals(context);
        List<String> currentSignals = profile.getSignals();
        if (currentSignals == null) {
            currentSignals = new ArrayList<>();
        }
        for (String signal : newSignals) {
            if (!currentSignals.contains(signal)) {
                currentSignals.add(signal);
            }
        }
        profile.setSignals(currentSignals);


        profile.setRiskLevel(calculateRiskLevel(profile.getRiskScore()));

        RiskProfile saved = riskProfileRepository.save(profile);
        return riskProfileMapper.toRiskProfileResponseFromRiskProfile(saved);
    }

    private RiskProfile getOrCreateProfile(String userId) {
        long id = Long.parseLong(userId.replaceAll("\\D", ""));
        return riskProfileRepository.findById(id)
                .orElseGet(() -> {
                    RiskProfile newProfile = new RiskProfile();
                    newProfile.setUserId(userId);
                    newProfile.setRiskScore(0.0);
                    newProfile.setRiskLevel(RiskLevel.LOW);
                    newProfile.setSignals(new ArrayList<>());
                    return riskProfileRepository.save(newProfile);
                });
    }

    private double calculateScore(EventContext context) {
        double score = 0;
        Map<String, String> meta = context.getMeta();

        if (meta != null) {

            if ("high".equalsIgnoreCase(meta.get("ip_risk"))) {
                score += 30;
            } else if ("medium".equalsIgnoreCase(meta.get("ip_risk"))) {
                score += 15;
            }


            if ("new".equalsIgnoreCase(meta.get("device"))) {
                score += 20;
            }


            if ("CryptoExchange".equalsIgnoreCase(meta.get("merchant"))) {
                score += 35;
            }


            if ("GiftCards".equalsIgnoreCase(meta.get("merchant"))) {
                score += 25;
            }


            if ("true".equalsIgnoreCase(meta.get("night_usage"))) {
                score += 15;
            }
        }


        if (context.getValue() != null && context.getValue() > 1000) {
            score += 20;
        }

        return score;
    }

    private List<String> detectSignals(EventContext context) {
        List<String> signals = new ArrayList<>();
        Map<String, String> meta = context.getMeta();

        if (meta != null) {

            if ("high".equalsIgnoreCase(meta.get("ip_risk")) &&
                    "new".equalsIgnoreCase(meta.get("device"))) {
                signals.add("high_ip_risk_new_device");
            }


            if ("CryptoExchange".equalsIgnoreCase(meta.get("merchant"))) {
                signals.add("crypto_payment");
            }


            if ("GiftCards".equalsIgnoreCase(meta.get("merchant"))) {
                signals.add("giftcard_burst");
            }

            if ("true".equalsIgnoreCase(meta.get("night_usage"))) {
                signals.add("night_high_usage");
            }
        }


        if (context.getValue() != null && context.getValue() > 1000) {
            signals.add("high_amount_payments");
        }

        return signals;
    }

    private RiskLevel calculateRiskLevel(double score) {
        if (score >= 70) {
            return RiskLevel.HIGH;
        } else if (score >= 40) {
            return RiskLevel.MEDIUM;
        }
        return RiskLevel.LOW;
    }
}