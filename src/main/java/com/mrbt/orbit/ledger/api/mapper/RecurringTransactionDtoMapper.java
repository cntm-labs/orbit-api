package com.mrbt.orbit.ledger.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.ledger.api.request.CreateRecurringTransactionRequest;
import com.mrbt.orbit.ledger.api.response.RecurringTransactionResponse;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;

@Component
public class RecurringTransactionDtoMapper
		extends
			GenericDtoMapper<CreateRecurringTransactionRequest, RecurringTransactionResponse, RecurringTransaction> {

	@Override
	public RecurringTransaction toDomain(CreateRecurringTransactionRequest request) {
		if (request == null) {
			return null;
		}
		return RecurringTransaction.builder().userId(request.userId()).accountId(request.accountId())
				.categoryId(request.categoryId()).amount(request.amount()).currencyCode(request.currencyCode())
				.description(request.description()).frequency(request.frequency()).nextOccurrence(request.startDate())
				.autoConfirm(request.autoConfirm()).build();
	}

	@Override
	public RecurringTransactionResponse toResponse(RecurringTransaction domain) {
		if (domain == null) {
			return null;
		}
		return RecurringTransactionResponse.builder().id(domain.getId()).userId(domain.getUserId())
				.accountId(domain.getAccountId()).categoryId(domain.getCategoryId()).amount(domain.getAmount())
				.currencyCode(domain.getCurrencyCode()).description(domain.getDescription())
				.frequency(EnumUtils.toStringOrNull(domain.getFrequency())).nextOccurrence(domain.getNextOccurrence())
				.autoConfirm(domain.getAutoConfirm()).status(EnumUtils.toStringOrNull(domain.getStatus()))
				.createdAt(domain.getCreatedAt()).updatedAt(domain.getUpdatedAt()).build();
	}

}
