package com.mrbt.orbit.budget.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.mrbt.orbit.budget.core.model.enums.GoalStatus;
import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
public class GoalEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal targetAmount;

	@Column(precision = 19, scale = 4)
	private BigDecimal currentAmount;

	private LocalDate targetDate;

	private UUID linkedAccountId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GoalStatus status;

}
