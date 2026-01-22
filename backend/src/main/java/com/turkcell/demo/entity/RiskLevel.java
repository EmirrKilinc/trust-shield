package com.turkcell.demo.entity;

public enum RiskLevel {
    LOW(0, 40, "Düşük risk"),
    MEDIUM(41, 70, "Orta risk"),
    HIGH(71, 100, "Yüksek risk");

    private final int minScore;
    private final int maxScore;
    private final String description;

    RiskLevel(int minScore, int maxScore, String description) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.description = description;
    }

    public static RiskLevel fromScore(double score) {
        if (score <= 40) return LOW;
        if (score <= 70) return MEDIUM;
        return HIGH;
    }
}