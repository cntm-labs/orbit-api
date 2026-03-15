package com.mrbt.orbit.security.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.security.infrastructure.entity.NotificationPreferenceEntity;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreferenceEntity, UUID> {

}
