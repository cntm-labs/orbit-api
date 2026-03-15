package com.mrbt.orbit.budget.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.budget.infrastructure.entity.BudgetItemEntity;

public interface BudgetItemRepository extends JpaRepository<BudgetItemEntity, UUID> {

}
