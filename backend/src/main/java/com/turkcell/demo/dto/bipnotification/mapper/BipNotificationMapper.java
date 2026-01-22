package com.turkcell.demo.dto.bipnotification.mapper;

import com.turkcell.demo.dto.bipnotification.request.BipNotificationRequest;
import com.turkcell.demo.dto.bipnotification.response.BipNotificationResponse;
import com.turkcell.demo.entity.BipNotification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BipNotificationMapper {
    public BipNotification toEntity(BipNotificationRequest request){
        return new BipNotification(
                request.id(),
                request.service(),
                request.message(),
                LocalDateTime.now()
        );
    }

    public BipNotificationResponse toDto(BipNotification notification){
        return new BipNotificationResponse(
                notification.getNotificationId(),
                notification.getUserId(),
                notification.getChannel(),
                notification.getMessage(),
                notification.getSentAt().toString()
        );
    }
}
