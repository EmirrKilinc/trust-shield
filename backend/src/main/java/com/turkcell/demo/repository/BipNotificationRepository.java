package com.turkcell.demo.repository;

import com.turkcell.demo.entity.BipNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BipNotificationRepository extends JpaRepository<BipNotification, Long> {
}
