package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.in.CreateGoalUseCase;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateGoalService implements CreateGoalUseCase {

	private final GoalRepositoryPort goalRepositoryPort;

	@Override
	@Transactional
	public Goal createGoal(Goal goal) {
		if (goal.getStatus() == null) {
			goal.setStatus(GoalStatus.IN_PROGRESS);
		}
		if (goal.getCurrentAmount() == null) {
			goal.setCurrentAmount(BigDecimal.ZERO);
		}
		return goalRepositoryPort.save(goal);
	}
}
