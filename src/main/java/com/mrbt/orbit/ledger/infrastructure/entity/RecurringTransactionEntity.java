package com.mrbt.orbit.ledger.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recurring_transactions")
@Getter
@Setter
@NoArgsConstructor
public class RecurringTransactionEntity extends BaseEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private UUID accountId;

	private UUID categoryId;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal amount;

	@Column(nullable = false)
	private String currencyCode;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Frequency frequency;

	@Column(nullable = false)
	private LocalDate nextOccurrence;

	@Column(nullable = false)
	private Boolean autoConfirm;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RecurringTransactionStatus status;

}
