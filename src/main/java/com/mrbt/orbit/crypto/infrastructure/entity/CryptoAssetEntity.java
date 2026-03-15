package com.mrbt.orbit.crypto.infrastructure.entity;

import java.math.BigDecimal;

import com.mrbt.orbit.common.infrastructure.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "crypto_assets")
@Getter
@Setter
@NoArgsConstructor
public class CryptoAssetEntity extends BaseEntity {

	@Column(nullable = false, unique = true)
	private String symbol;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String coingeckoId;

	@Column(precision = 19, scale = 8)
	private BigDecimal currentPriceUsd;

	private Integer marketCapRank;

}
