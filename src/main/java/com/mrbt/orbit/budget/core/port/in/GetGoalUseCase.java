package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.core.port.in.UseCase;

import java.util.Optional;
import java.util.UUID;

public interface GetGoalUseCase extends UseCase {
	Optional<Goal> getGoalById(UUID goalId);
	PageResult<Goal> getGoalsByUserId(UUID userId, int page, int size);
}
