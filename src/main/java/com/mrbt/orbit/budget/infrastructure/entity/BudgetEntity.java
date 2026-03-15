package com.mrbt.orbit.budget.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.budget.core.model.enums.BudgetPeriodType;
import com.mrbt.orbit.budget.core.model.enums.BudgetStatus;
import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
public class BudgetEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BudgetPeriodType periodType;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal totalAmount;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BudgetStatus status;

	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetItemEntity> items = new ArrayList<>();

}
