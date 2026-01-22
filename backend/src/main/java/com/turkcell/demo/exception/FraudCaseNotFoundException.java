package com.turkcell.demo.exception;

public class FraudCaseNotFoundException extends RuntimeException {
    public FraudCaseNotFoundException(String message) {
        super(message);
    }
}
