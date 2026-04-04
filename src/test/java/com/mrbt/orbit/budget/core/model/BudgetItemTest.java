package com.mrbt.orbit.budget.core.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class BudgetItemTest {

	@Test
	void isAlertThresholdExceeded_shouldReturnTrueWhenOverThreshold() {
		BudgetItem item = BudgetItem.builder().spentAmount(BigDecimal.valueOf(80))
				.allocatedAmount(BigDecimal.valueOf(100)).alertThresholdPct(90).build();
		assertThat(item.isAlertThresholdExceeded(BigDecimal.valueOf(15))).isTrue();
	}

	@Test
	void isAlertThresholdExceeded_shouldReturnFalseWhenUnderThreshold() {
		BudgetItem item = BudgetItem.builder().spentAmount(BigDecimal.valueOf(50))
				.allocatedAmount(BigDecimal.valueOf(100)).alertThresholdPct(90).build();
		assertThat(item.isAlertThresholdExceeded(BigDecimal.valueOf(10))).isFalse();
	}

	@Test
	void isAlertThresholdExceeded_shouldReturnFalseWhenNoThresholdSet() {
		BudgetItem item = BudgetItem.builder().spentAmount(BigDecimal.valueOf(99))
				.allocatedAmount(BigDecimal.valueOf(100)).build();
		assertThat(item.isAlertThresholdExceeded(BigDecimal.valueOf(10))).isFalse();
	}

}
