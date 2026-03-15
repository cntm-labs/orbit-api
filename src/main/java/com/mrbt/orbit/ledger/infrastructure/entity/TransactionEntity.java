package com.mrbt.orbit.ledger.infrastructure.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.SoftDeletableEntity;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity extends SoftDeletableEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private AccountEntity account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	private UUID transferPairId;

	private UUID recurringTransactionId;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal amount;

	@Column(nullable = false)
	private String currencyCode;

	@Column(precision = 19, scale = 8)
	private BigDecimal exchangeRate;

	private String description;

	@Column(nullable = false)
	private Instant transactionDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus status;

	private String plaidTransactionId;

	@Column(nullable = false)
	private Boolean isReviewed;

	@ManyToMany
	@JoinTable(name = "transaction_tags", joinColumns = @JoinColumn(name = "transaction_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<TagEntity> tags;

}
