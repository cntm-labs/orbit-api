package com.mrbt.orbit.budget.core.model;

import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.common.core.model.BaseDomainModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Goal extends BaseDomainModel {
	private UUID userId;
	private String name;
	private BigDecimal targetAmount;
	private BigDecimal currentAmount;
	private LocalDate targetDate;
	private UUID linkedAccountId;
	private GoalStatus status;
}
