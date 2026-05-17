package com.mrbt.orbit.crypto.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
public class CryptoPortfolioSnapshot extends BaseDomainModel {
	private UUID userId;
	private LocalDate snapshotDate;
	private BigDecimal totalValueUsd;
	private String holdingsJson;
}
