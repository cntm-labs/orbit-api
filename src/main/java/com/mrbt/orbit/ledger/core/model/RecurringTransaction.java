package com.mrbt.orbit.ledger.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecurringTransaction extends BaseDomainModel {

	private UUID userId;

	private UUID accountId;

	private UUID categoryId;

	private BigDecimal amount;

	private String currencyCode;

	private String description;

	private Frequency frequency;

	private LocalDate nextOccurrence;

	private Boolean autoConfirm;

	private RecurringTransactionStatus status;

	public void togglePause() {
		this.status = (this.status == RecurringTransactionStatus.ACTIVE)
				? RecurringTransactionStatus.PAUSED
				: RecurringTransactionStatus.ACTIVE;
	}

	public void applyDefaults() {
		if (this.status == null) {
			this.status = RecurringTransactionStatus.ACTIVE;
		}
		if (this.autoConfirm == null) {
			this.autoConfirm = true;
		}
	}

	public void cancel() {
		this.status = RecurringTransactionStatus.CANCELLED;
	}

}
