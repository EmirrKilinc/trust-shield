package com.turkcell.demo.exception;

public class RiskProfileNotFoundException extends RuntimeException {
    public RiskProfileNotFoundException(String message) {
        super(message);
    }
}
