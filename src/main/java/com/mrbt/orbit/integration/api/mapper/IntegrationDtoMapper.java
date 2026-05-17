package com.mrbt.orbit.integration.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.integration.api.response.ExchangeRateResponse;
import com.mrbt.orbit.integration.api.response.PlaidLinkResponse;
import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.core.model.PlaidLink;

@Component
public class IntegrationDtoMapper {

	public ExchangeRateResponse toResponse(ExchangeRate domain) {
		if (domain == null) {
			return null;
		}
		return new ExchangeRateResponse(domain.getId(), domain.getBaseCurrency(), domain.getTargetCurrency(),
				domain.getRate(), domain.getSource(), domain.getFetchedAt());
	}

	public PlaidLinkResponse toResponse(PlaidLink domain) {
		if (domain == null) {
			return null;
		}
		return new PlaidLinkResponse(domain.getId(), domain.getUserId(), domain.getInstitutionName(),
				domain.getStatus(), domain.getErrorCode());
	}
}
