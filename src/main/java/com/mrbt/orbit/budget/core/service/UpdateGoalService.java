package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.in.DeleteGoalUseCase;
import com.mrbt.orbit.budget.core.port.in.UpdateGoalUseCase;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateGoalService implements UpdateGoalUseCase, DeleteGoalUseCase {

	private final GoalRepositoryPort goalRepositoryPort;

	@Override
	@Transactional
	public Goal updateGoal(UUID goalId, String name, BigDecimal targetAmount, LocalDate targetDate) {
		Goal goal = goalRepositoryPort.findById(goalId)
				.orElseThrow(() -> new ResourceNotFoundException("Goal", "ID", goalId));
		if (name != null) {
			goal.setName(name);
		}
		if (targetAmount != null) {
			goal.setTargetAmount(targetAmount);
		}
		if (targetDate != null) {
			goal.setTargetDate(targetDate);
		}
		return goalRepositoryPort.save(goal);
	}

	@Override
	@Transactional
	public void cancelGoal(UUID goalId) {
		Goal goal = goalRepositoryPort.findById(goalId)
				.orElseThrow(() -> new ResourceNotFoundException("Goal", "ID", goalId));
		goal.setStatus(GoalStatus.CANCELLED);
		goalRepositoryPort.save(goal);
	}
}
