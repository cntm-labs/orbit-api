package com.mrbt.orbit.budget.core.model;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BudgetItem extends BaseDomainModel {
	private UUID budgetId;
	private UUID categoryId;
	private BigDecimal allocatedAmount;
	private BigDecimal spentAmount;
	private Integer alertThresholdPct;

	public boolean isAlertThresholdExceeded(BigDecimal additionalAmount) {
		if (this.alertThresholdPct == null) {
			return false;
		}
		BigDecimal newSpent = this.spentAmount.add(additionalAmount);
		BigDecimal pct = newSpent.multiply(BigDecimal.valueOf(100)).divide(this.allocatedAmount, 0,
				RoundingMode.HALF_UP);
		return pct.intValue() >= this.alertThresholdPct;
	}

}
