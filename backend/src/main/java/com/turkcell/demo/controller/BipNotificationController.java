package com.turkcell.demo.controller;

import com.turkcell.demo.dto.bipnotification.request.BipNotificationRequest;
import com.turkcell.demo.dto.bipnotification.response.BipNotificationResponse;
import com.turkcell.demo.service.BipNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class BipNotificationController {

    private final BipNotificationService bipNotificationService;

    public BipNotificationController(BipNotificationService bipNotificationService) {
        this.bipNotificationService = bipNotificationService;
    }

    @PostMapping
    public ResponseEntity<BipNotificationResponse> sendNotification(@RequestBody BipNotificationRequest request) {
        return ResponseEntity.ok(bipNotificationService.sendBipNotification(request));
    }

    @GetMapping
    public ResponseEntity<List<BipNotificationResponse>> getAllNotifications() {
        return ResponseEntity.ok(bipNotificationService.getAllNotifications());
    }
}