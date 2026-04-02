package com.mrbt.orbit.ledger.infrastructure.mapper;

import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class TransactionMapper {

	public Transaction toDomain(TransactionEntity entity) {
		if (entity == null)
			return null;

		return Transaction.builder().id(entity.getId()).accountId(entity.getAccount().getId())
				.categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
				.transferPairId(entity.getTransferPairId()).recurringTransactionId(entity.getRecurringTransactionId())
				.amount(entity.getAmount()).currencyCode(entity.getCurrencyCode())
				.exchangeRate(entity.getExchangeRate()).description(entity.getDescription())
				.transactionDate(entity.getTransactionDate()).status(entity.getStatus())
				.plaidTransactionId(entity.getPlaidTransactionId()).isReviewed(entity.getIsReviewed())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.build();
	}

	public TransactionEntity toEntity(Transaction domain, AccountEntity account, CategoryEntity category) {
		if (domain == null)
			return null;

		TransactionEntity entity = new TransactionEntity();
		entity.setId(domain.getId());
		entity.setAccount(account);
		entity.setCategory(category);
		entity.setTransferPairId(domain.getTransferPairId());
		entity.setRecurringTransactionId(domain.getRecurringTransactionId());
		entity.setAmount(domain.getAmount());
		entity.setCurrencyCode(domain.getCurrencyCode());
		entity.setExchangeRate(domain.getExchangeRate());
		entity.setDescription(domain.getDescription());
		entity.setTransactionDate(domain.getTransactionDate());
		entity.setStatus(domain.getStatus());
		entity.setPlaidTransactionId(domain.getPlaidTransactionId());
		entity.setIsReviewed(domain.getIsReviewed());

		return entity;
	}

}
