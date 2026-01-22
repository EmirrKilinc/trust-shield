package com.turkcell.demo.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorDetails(LocalDateTime timeStamp,
                           String message,
                           String path,
                           int status,
                           String error,
                           Map<String, String> validationErrors) {
}