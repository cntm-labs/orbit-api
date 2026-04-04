package com.mrbt.orbit.ledger.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;

class AccountMapperTest {

	private final AccountMapper mapper = new AccountMapper();

	@Test
	void toDomain_fromEntity_mapsAllFields() {
		AccountEntity entity = new AccountEntity();
		UUID id = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		entity.setId(id);
		entity.setUserId(userId);
		entity.setName("Savings");
		entity.setType(AccountType.BANK);
		entity.setCurrencyCode("USD");
		entity.setCurrentBalance(new BigDecimal("1500.00"));
		entity.setPlaidAccountId("plaid_123");
		entity.setStatus(AccountStatus.ACTIVE);
		entity.setCreatedAt(Instant.parse("2026-01-01T00:00:00Z"));
		entity.setUpdatedAt(Instant.parse("2026-01-02T00:00:00Z"));

		Account result = mapper.toDomain(entity);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getName()).isEqualTo("Savings");
		assertThat(result.getType()).isEqualTo(AccountType.BANK);
		assertThat(result.getCurrencyCode()).isEqualTo("USD");
		assertThat(result.getCurrentBalance()).isEqualByComparingTo("1500.00");
		assertThat(result.getPlaidAccountId()).isEqualTo("plaid_123");
		assertThat(result.getStatus()).isEqualTo(AccountStatus.ACTIVE);
	}

	@Test
	void toDomain_fromEntity_returnsNullForNull() {
		assertThat(mapper.toDomain((AccountEntity) null)).isNull();
	}

	@Test
	void toEntity_mapsAllFields() {
		UUID id = UUID.randomUUID();
		UUID userId = UUID.randomUUID();
		Account domain = Account.builder().id(id).userId(userId).name("Checking").type(AccountType.CREDIT)
				.currencyCode("THB").currentBalance(new BigDecimal("2000.00")).plaidAccountId("plaid_456")
				.status(AccountStatus.ACTIVE).build();

		AccountEntity result = mapper.toEntity(domain);

		assertThat(result.getId()).isEqualTo(id);
		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getName()).isEqualTo("Checking");
		assertThat(result.getType()).isEqualTo(AccountType.CREDIT);
		assertThat(result.getCurrencyCode()).isEqualTo("THB");
		assertThat(result.getCurrentBalance()).isEqualByComparingTo("2000.00");
	}

	@Test
	void toEntity_returnsNullForNull() {
		assertThat(mapper.toEntity(null)).isNull();
	}

}
