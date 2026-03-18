package com.mrbt.orbit.budget.core.port.in;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.common.core.port.in.UseCase;

public interface CreateGoalUseCase extends UseCase {
	Goal createGoal(Goal goal);
}
