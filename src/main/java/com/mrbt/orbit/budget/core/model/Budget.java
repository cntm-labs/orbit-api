package com.mrbt.orbit.budget.core.model;

import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.common.core.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Budget extends BaseDomainModel {
	private UUID userId;
	private String name;
	private BudgetPeriodType periodType;
	private LocalDate startDate;
	private LocalDate endDate;
	private BigDecimal totalAmount;
	private BudgetStatus status;
	private List<BudgetItem> items;
}
