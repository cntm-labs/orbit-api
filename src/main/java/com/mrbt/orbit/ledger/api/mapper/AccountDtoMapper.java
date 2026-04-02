package com.mrbt.orbit.ledger.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.ledger.api.request.CreateAccountRequest;
import com.mrbt.orbit.ledger.api.response.AccountResponse;
import com.mrbt.orbit.ledger.core.model.Account;

@Component
public class AccountDtoMapper extends GenericDtoMapper<CreateAccountRequest, AccountResponse, Account> {

	@Override
	public Account toDomain(CreateAccountRequest request) {
		if (request == null) {
			return null;
		}
		return Account.builder().userId(request.userId()).name(request.name()).type(request.type())
				.currencyCode(request.currencyCode()).currentBalance(request.initialBalance())
				.plaidAccountId(request.plaidAccountId()).build();
	}

	@Override
	public AccountResponse toResponse(Account domain) {
		if (domain == null) {
			return null;
		}
		return AccountResponse.builder().id(domain.getId()).userId(domain.getUserId()).name(domain.getName())
				.type(EnumUtils.toStringOrNull(domain.getType())).currencyCode(domain.getCurrencyCode())
				.currentBalance(domain.getCurrentBalance()).plaidAccountId(domain.getPlaidAccountId())
				.status(EnumUtils.toStringOrNull(domain.getStatus())).createdAt(domain.getCreatedAt())
				.updatedAt(domain.getUpdatedAt()).build();
	}

}
