package com.mrbt.orbit.ledger.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;

class TransactionMapperTest {

	private final TransactionMapper mapper = new TransactionMapper();

	@Test
	void toDomain_fromEntity_mapsAllFields() {
		UUID txId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		UUID transferPairId = UUID.randomUUID();

		AccountEntity account = new AccountEntity();
		account.setId(accountId);

		CategoryEntity category = new CategoryEntity();
		category.setId(categoryId);

		TransactionEntity entity = new TransactionEntity();
		entity.setId(txId);
		entity.setAccount(account);
		entity.setCategory(category);
		entity.setTransferPairId(transferPairId);
		entity.setAmount(new BigDecimal("-50.00"));
		entity.setCurrencyCode("USD");
		entity.setExchangeRate(BigDecimal.ONE);
		entity.setDescription("Lunch");
		entity.setTransactionDate(Instant.parse("2026-01-15T12:00:00Z"));
		entity.setStatus(TransactionStatus.COMPLETED);
		entity.setPlaidTransactionId("plaid_tx_1");
		entity.setIsReviewed(true);
		entity.setCreatedAt(Instant.parse("2026-01-15T12:00:00Z"));

		Transaction result = mapper.toDomain(entity);

		assertThat(result.getId()).isEqualTo(txId);
		assertThat(result.getAccountId()).isEqualTo(accountId);
		assertThat(result.getCategoryId()).isEqualTo(categoryId);
		assertThat(result.getTransferPairId()).isEqualTo(transferPairId);
		assertThat(result.getAmount()).isEqualByComparingTo("-50.00");
		assertThat(result.getCurrencyCode()).isEqualTo("USD");
		assertThat(result.getDescription()).isEqualTo("Lunch");
		assertThat(result.getStatus()).isEqualTo(TransactionStatus.COMPLETED);
		assertThat(result.getIsReviewed()).isTrue();
	}

	@Test
	void toDomain_fromEntity_handlesNullCategory() {
		AccountEntity account = new AccountEntity();
		account.setId(UUID.randomUUID());

		TransactionEntity entity = new TransactionEntity();
		entity.setId(UUID.randomUUID());
		entity.setAccount(account);
		entity.setCategory(null);
		entity.setAmount(new BigDecimal("100.00"));
		entity.setStatus(TransactionStatus.COMPLETED);

		Transaction result = mapper.toDomain(entity);

		assertThat(result.getCategoryId()).isNull();
	}

	@Test
	void toDomain_fromEntity_returnsNullForNull() {
		assertThat(mapper.toDomain((TransactionEntity) null)).isNull();
	}

	@Test
	void toEntity_mapsAllFields() {
		UUID txId = UUID.randomUUID();
		AccountEntity account = new AccountEntity();
		account.setId(UUID.randomUUID());
		CategoryEntity category = new CategoryEntity();
		category.setId(UUID.randomUUID());

		Transaction domain = Transaction.builder().id(txId).amount(new BigDecimal("200.00")).currencyCode("THB")
				.exchangeRate(new BigDecimal("35.0")).description("Salary").transactionDate(Instant.now())
				.status(TransactionStatus.COMPLETED).plaidTransactionId("plaid_tx_2").isReviewed(false).build();

		TransactionEntity result = mapper.toEntity(domain, account, category);

		assertThat(result.getId()).isEqualTo(txId);
		assertThat(result.getAccount()).isEqualTo(account);
		assertThat(result.getCategory()).isEqualTo(category);
		assertThat(result.getAmount()).isEqualByComparingTo("200.00");
		assertThat(result.getDescription()).isEqualTo("Salary");
	}

	@Test
	void toEntity_returnsNullForNull() {
		assertThat(mapper.toEntity(null, null, null)).isNull();
	}

}
