package com.mrbt.orbit.integration.infrastructure.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.mrbt.orbit.common.infrastructure.entity.CreatedOnlyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exchange_rates")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateEntity extends CreatedOnlyEntity {

	@Column(nullable = false)
	private String baseCurrency;

	@Column(nullable = false)
	private String targetCurrency;

	@Column(nullable = false, precision = 19, scale = 8)
	private BigDecimal rate;

	private String source;

	@Column(nullable = false)
	private Instant fetchedAt;

}
