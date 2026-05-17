package com.mrbt.orbit.integration.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.integration.core.model.ExchangeRate;
import com.mrbt.orbit.integration.infrastructure.entity.ExchangeRateEntity;

@Component
public class ExchangeRateEntityMapper extends AbstractNullSafeMapper<ExchangeRateEntity, ExchangeRate> {

	@Override
	public ExchangeRate toDomain(ExchangeRateEntity entity) {
		if (entity == null) {
			return null;
		}
		return ExchangeRate.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.baseCurrency(entity.getBaseCurrency()).targetCurrency(entity.getTargetCurrency())
				.rate(entity.getRate()).source(entity.getSource())
				.fetchedAt(entity.getFetchedAt() != null ? entity.getFetchedAt().atOffset(ZoneOffset.UTC) : null)
				.build();
	}

	@Override
	public ExchangeRateEntity toEntity(ExchangeRate domain) {
		if (domain == null) {
			return null;
		}
		ExchangeRateEntity entity = new ExchangeRateEntity();
		entity.setId(domain.getId());
		entity.setBaseCurrency(domain.getBaseCurrency());
		entity.setTargetCurrency(domain.getTargetCurrency());
		entity.setRate(domain.getRate());
		entity.setSource(domain.getSource());
		entity.setFetchedAt(domain.getFetchedAt() != null ? domain.getFetchedAt().toInstant() : null);
		return entity;
	}
}
