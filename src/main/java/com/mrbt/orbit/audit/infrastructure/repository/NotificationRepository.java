package com.mrbt.orbit.audit.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.audit.infrastructure.entity.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {

}
