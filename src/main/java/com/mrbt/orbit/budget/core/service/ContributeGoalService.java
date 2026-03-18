package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.budget.core.port.in.ContributeGoalUseCase;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import com.mrbt.orbit.common.exception.BadRequestException;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContributeGoalService implements ContributeGoalUseCase {

	private final GoalRepositoryPort goalRepositoryPort;

	@Override
	@Transactional
	public Goal contribute(UUID goalId, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BadRequestException("Contribution amount must be positive");
		}

		Goal goal = goalRepositoryPort.findById(goalId)
				.orElseThrow(() -> new ResourceNotFoundException("Goal", "ID", goalId));

		if (goal.getLinkedAccountId() != null) {
			throw new BadRequestException("Cannot manually contribute to a goal linked to an account");
		}

		goal.setCurrentAmount(goal.getCurrentAmount().add(amount));

		if (goal.getCurrentAmount().compareTo(goal.getTargetAmount()) >= 0) {
			goal.setStatus(GoalStatus.ACHIEVED);
		}

		return goalRepositoryPort.save(goal);
	}
}
