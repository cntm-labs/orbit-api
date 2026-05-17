package com.mrbt.orbit.crypto.core.model;

import java.math.BigDecimal;

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
public class CryptoAsset extends BaseDomainModel {
	private String symbol;
	private String name;
	private String coingeckoId;
	private BigDecimal currentPriceUsd;
	private Integer marketCapRank;
}
