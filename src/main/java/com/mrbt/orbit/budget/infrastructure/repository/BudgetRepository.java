package com.mrbt.orbit.budget.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.budget.infrastructure.entity.BudgetEntity;

public interface BudgetRepository extends JpaRepository<BudgetEntity, UUID> {

	Page<BudgetEntity> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

}
