package com.mrbt.orbit.budget.infrastructure.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "budget_items")
@Getter
@Setter
@NoArgsConstructor
public class BudgetItemEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID categoryId;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal allocatedAmount;

	@Column(precision = 19, scale = 4)
	private BigDecimal spentAmount;

	private Integer alertThresholdPct;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id", nullable = false)
	private BudgetEntity budget;

}
