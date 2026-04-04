package com.mrbt.orbit.ledger.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.ledger.api.request.CreateTransactionRequest;
import com.mrbt.orbit.ledger.api.response.TransactionResponse;
import com.mrbt.orbit.ledger.core.model.Transaction;

@Component
public class TransactionDtoMapper extends GenericDtoMapper<CreateTransactionRequest, TransactionResponse, Transaction> {

	@Override
	public Transaction toDomain(CreateTransactionRequest request) {
		if (request == null) {
			return null;
		}
		return Transaction.builder().accountId(request.accountId()).categoryId(request.categoryId())
				.amount(request.amount()).currencyCode(request.currencyCode()).description(request.description())
				.transactionDate(request.transactionDate()).build();
	}

	@Override
	public TransactionResponse toResponse(Transaction domain) {
		if (domain == null) {
			return null;
		}
		return TransactionResponse.builder().id(domain.getId()).accountId(domain.getAccountId())
				.categoryId(domain.getCategoryId()).amount(domain.getAmount()).currencyCode(domain.getCurrencyCode())
				.exchangeRate(domain.getExchangeRate()).description(domain.getDescription())
				.transactionDate(domain.getTransactionDate()).status(EnumUtils.toStringOrNull(domain.getStatus()))
				.isReviewed(domain.getIsReviewed()).createdAt(domain.getCreatedAt()).build();
	}

}
