package com.mrbt.orbit.crypto.infrastructure.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.mrbt.orbit.common.infrastructure.entity.CreatedOnlyEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crypto_portfolio_snapshots")
@Getter
@Setter
@NoArgsConstructor
public class CryptoPortfolioSnapshotEntity extends CreatedOnlyEntity {

	@Column(nullable = false)
	private UUID userId;

	@Column(nullable = false)
	private LocalDate snapshotDate;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal totalValueUsd;

	@Column(columnDefinition = "text")
	private String holdingsJson;

}
