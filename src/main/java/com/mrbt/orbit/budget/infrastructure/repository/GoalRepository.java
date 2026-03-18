package com.mrbt.orbit.budget.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.budget.infrastructure.entity.GoalEntity;

public interface GoalRepository extends JpaRepository<GoalEntity, UUID> {

	Page<GoalEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

}
