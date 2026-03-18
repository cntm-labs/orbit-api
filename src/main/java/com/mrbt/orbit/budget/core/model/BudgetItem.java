package com.mrbt.orbit.budget.core.model;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
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
}
