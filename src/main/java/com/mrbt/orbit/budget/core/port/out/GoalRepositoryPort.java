package com.mrbt.orbit.budget.core.port.out;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.core.model.PageResult;

import java.util.Optional;
import java.util.UUID;

public interface GoalRepositoryPort {
	Goal save(Goal goal);
	Optional<Goal> findById(UUID id);
	PageResult<Goal> findByUserId(UUID userId, int page, int size);
}
