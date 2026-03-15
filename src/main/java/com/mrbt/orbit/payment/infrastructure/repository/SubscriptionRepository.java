package com.mrbt.orbit.payment.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.payment.infrastructure.entity.SubscriptionEntity;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, UUID> {

}
