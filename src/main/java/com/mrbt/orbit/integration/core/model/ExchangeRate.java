package com.mrbt.orbit.integration.core.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.mrbt.orbit.common.core.model.BaseDomainModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate extends BaseDomainModel {
	private String baseCurrency;
	private String targetCurrency;
	private BigDecimal rate;
	private String source;
	private OffsetDateTime fetchedAt;
}
