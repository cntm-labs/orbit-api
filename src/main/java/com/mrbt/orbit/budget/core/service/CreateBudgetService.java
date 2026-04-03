package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.port.in.CreateBudgetUseCase;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBudgetService implements CreateBudgetUseCase {

	private final BudgetRepositoryPort budgetRepositoryPort;

	@Override
	@Transactional
	public Budget createBudget(Budget budget) {
		if (!budget.getEndDate().isAfter(budget.getStartDate())) {
			throw new BadRequestException("End date must be after start date");
		}
		budget.applyDefaults();
		budget.initializeItems();
		budget.calculateTotalAmount();
		return budgetRepositoryPort.save(budget);
	}
}
