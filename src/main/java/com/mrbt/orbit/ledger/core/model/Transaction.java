package com.mrbt.orbit.ledger.core.model;

import com.mrbt.orbit.common.core.model.BaseDomainModel;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Transaction extends BaseDomainModel {
	private UUID accountId;
	private UUID categoryId;
	private UUID transferPairId;
	private UUID recurringTransactionId;
	private BigDecimal amount;
	private String currencyCode;
	private BigDecimal exchangeRate;
	private String description;
	private Instant transactionDate;
	private TransactionStatus status;
	private String plaidTransactionId;
	private Boolean isReviewed;
}
