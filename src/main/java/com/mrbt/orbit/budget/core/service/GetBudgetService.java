package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.budget.core.port.in.ArchiveBudgetUseCase;
import com.mrbt.orbit.budget.core.port.in.GetBudgetUseCase;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetBudgetService implements GetBudgetUseCase, ArchiveBudgetUseCase {

	private final BudgetRepositoryPort budgetRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Budget> getBudgetById(UUID budgetId) {
		return budgetRepositoryPort.findById(budgetId);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<Budget> getBudgetsByUserId(UUID userId, int page, int size) {
		return budgetRepositoryPort.findByUserId(userId, page, size);
	}

	@Override
	@Transactional
	public void archiveBudget(UUID budgetId) {
		Budget budget = budgetRepositoryPort.findById(budgetId)
				.orElseThrow(() -> new ResourceNotFoundException("Budget", "ID", budgetId));
		budget.setStatus(BudgetStatus.ARCHIVED);
		budgetRepositoryPort.save(budget);
	}
}
