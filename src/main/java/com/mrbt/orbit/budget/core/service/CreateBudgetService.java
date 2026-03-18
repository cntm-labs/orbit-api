package com.mrbt.orbit.budget.core.service;

import com.mrbt.orbit.budget.core.model.Budget;
import com.mrbt.orbit.budget.core.model.BudgetItem;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.budget.core.port.in.CreateBudgetUseCase;
import com.mrbt.orbit.budget.core.port.out.BudgetRepositoryPort;
import com.mrbt.orbit.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

		if (budget.getStatus() == null) {
			budget.setStatus(BudgetStatus.ACTIVE);
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		if (budget.getItems() != null) {
			for (BudgetItem item : budget.getItems()) {
				if (item.getSpentAmount() == null) {
					item.setSpentAmount(BigDecimal.ZERO);
				}
				totalAmount = totalAmount.add(item.getAllocatedAmount());
			}
		}
		budget.setTotalAmount(totalAmount);

		return budgetRepositoryPort.save(budget);
	}
}
