package com.mrbt.orbit.crypto.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.crypto.core.model.CryptoPortfolioSnapshot;
import com.mrbt.orbit.crypto.core.port.out.CryptoAssetRepositoryPort;
import com.mrbt.orbit.crypto.core.port.out.CryptoPortfolioSnapshotRepositoryPort;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

@ExtendWith(MockitoExtension.class)
class CryptoServiceTest {

	@Mock
	private CryptoAssetRepositoryPort assetRepositoryPort;
	@Mock
	private CryptoPortfolioSnapshotRepositoryPort snapshotRepositoryPort;
	@Mock
	private AccountRepositoryPort accountRepositoryPort;

	@InjectMocks
	private CryptoService cryptoService;

	@Test
	void createSnapshot_ShouldCalculateTotalAndSave() {
		// Given
		UUID userId = UUID.randomUUID();
		Account cryptoAccount = Account.builder().type(AccountType.CRYPTO).currentBalance(new BigDecimal("1000.00"))
				.build();
		Account cashAccount = Account.builder().type(AccountType.CASH).currentBalance(new BigDecimal("500.00")).build();

		when(accountRepositoryPort.findByUserId(userId)).thenReturn(List.of(cryptoAccount, cashAccount));
		when(snapshotRepositoryPort.save(any(CryptoPortfolioSnapshot.class))).thenAnswer(i -> i.getArguments()[0]);

		// When
		CryptoPortfolioSnapshot result = cryptoService.createSnapshot(userId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getTotalValueUsd()).isEqualByComparingTo("1000.00");
		verify(snapshotRepositoryPort).save(any(CryptoPortfolioSnapshot.class));
	}
}
