package com.turkcell.demo.service;

import com.turkcell.demo.dto.bipnotification.mapper.BipNotificationMapper;
import com.turkcell.demo.dto.bipnotification.request.BipNotificationRequest;
import com.turkcell.demo.dto.bipnotification.response.BipNotificationResponse;
import com.turkcell.demo.entity.BipNotification;
import com.turkcell.demo.repository.BipNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BipNotificationService {
    private final BipNotificationMapper bipNotificationMapper;
    private final BipNotificationRepository bipNotificationRepository;

    public BipNotificationService(BipNotificationMapper bipNotificationMapper, BipNotificationRepository bipNotificationRepository) {
        this.bipNotificationMapper = bipNotificationMapper;
        this.bipNotificationRepository = bipNotificationRepository;
    }

    public BipNotificationResponse sendBipNotification(BipNotificationRequest request) {
        BipNotification notification = bipNotificationMapper.toEntity(request);
        BipNotification savedNotification = bipNotificationRepository.save(notification);
        return bipNotificationMapper.toDto(savedNotification);
    }

    public List<BipNotificationResponse> getAllNotifications() {
        return bipNotificationRepository.findAll().stream()
                .map(bipNotificationMapper::toDto)
                .toList();
    }
}
