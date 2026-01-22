package com.turkcell.demo.exception;

public class RiskRuleNotFoundException extends RuntimeException {
    public RiskRuleNotFoundException(String message) {
        super(message);
    }
}
