package com.mrbt.orbit.budget.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;

class BudgetTest {

	@Test
	void applyDefaults_shouldSetActiveStatus() {
		Budget budget = Budget.builder().build();
		budget.applyDefaults();
		assertThat(budget.getStatus()).isEqualTo(BudgetStatus.ACTIVE);
	}

	@Test
	void calculateTotalAmount_shouldSumAllocatedAmounts() {
		BudgetItem item1 = BudgetItem.builder().allocatedAmount(BigDecimal.valueOf(100)).build();
		BudgetItem item2 = BudgetItem.builder().allocatedAmount(BigDecimal.valueOf(200)).build();
		Budget budget = Budget.builder().items(List.of(item1, item2)).build();
		budget.calculateTotalAmount();
		assertThat(budget.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(300));
	}

	@Test
	void calculateTotalAmount_shouldHandleNullItems() {
		Budget budget = Budget.builder().build();
		budget.calculateTotalAmount();
		assertThat(budget.getTotalAmount()).isEqualByComparingTo(BigDecimal.ZERO);
	}

}
