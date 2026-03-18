package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Goal;
import com.mrbt.orbit.budget.core.port.in.GetGoalUseCase;
import com.mrbt.orbit.budget.core.port.out.GoalRepositoryPort;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetGoalService implements GetGoalUseCase {

	private final GoalRepositoryPort goalRepositoryPort;
	private final AccountRepositoryPort accountRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Goal> getGoalById(UUID goalId) {
		return goalRepositoryPort.findById(goalId).map(this::resolveCurrentAmount);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<Goal> getGoalsByUserId(UUID userId, int page, int size) {
		PageResult<Goal> result = goalRepositoryPort.findByUserId(userId, page, size);
		return new PageResult<>(result.content().stream().map(this::resolveCurrentAmount).toList(),
				result.totalElements(), result.totalPages(), result.page(), result.size());
	}

	private Goal resolveCurrentAmount(Goal goal) {
		if (goal.getLinkedAccountId() != null) {
			accountRepositoryPort.findById(goal.getLinkedAccountId())
					.ifPresent(account -> goal.setCurrentAmount(account.getCurrentBalance()));
		}
		return goal;
	}
}
