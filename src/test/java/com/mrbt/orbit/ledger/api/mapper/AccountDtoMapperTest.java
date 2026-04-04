package com.mrbt.orbit.ledger.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.api.request.CreateAccountRequest;
import com.mrbt.orbit.ledger.api.response.AccountResponse;
import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;

class AccountDtoMapperTest {

	private final AccountDtoMapper mapper = new AccountDtoMapper();

	@Test
	void toDomain_mapsAllFields() {
		UUID userId = UUID.randomUUID();

		CreateAccountRequest request = CreateAccountRequest.builder().userId(userId).name("Savings")
				.type(AccountType.BANK).currencyCode("USD").initialBalance(new BigDecimal("1000"))
				.plaidAccountId("plaid_123").build();

		Account result = mapper.toDomain(request);

		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getName()).isEqualTo("Savings");
		assertThat(result.getType()).isEqualTo(AccountType.BANK);
		assertThat(result.getCurrencyCode()).isEqualTo("USD");
		assertThat(result.getCurrentBalance()).isEqualByComparingTo(new BigDecimal("1000"));
		assertThat(result.getPlaidAccountId()).isEqualTo("plaid_123");
	}

	@Test
	void toDomain_returnsNullForNull() {
		assertThat(mapper.toDomain(null)).isNull();
	}

	@Test
	void toResponse_mapsAllFields() {
		UUID id = UUID.randomUUID();
		OffsetDateTime now = OffsetDateTime.now();

		Account domain = Account.builder().id(id).userId(UUID.randomUUID()).name("Checking").type(AccountType.CREDIT)
				.currencyCode("THB").currentBalance(BigDecimal.valueOf(5000)).plaidAccountId("plaid_456")
				.status(AccountStatus.ACTIVE).createdAt(now).updatedAt(now).build();

		AccountResponse result = mapper.toResponse(domain);

		assertThat(result.id()).isEqualTo(id);
		assertThat(result.type()).isEqualTo("CREDIT");
		assertThat(result.status()).isEqualTo("ACTIVE");
		assertThat(result.createdAt()).isEqualTo(now);
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullEnums() {
		Account domain = Account.builder().name("Test").build();
		AccountResponse result = mapper.toResponse(domain);
		assertThat(result.type()).isNull();
		assertThat(result.status()).isNull();
	}

	@Test
	void toResponseList_convertsList() {
		Account a1 = Account.builder().name("A1").build();
		Account a2 = Account.builder().name("A2").build();
		List<AccountResponse> result = mapper.toResponseList(List.of(a1, a2));
		assertThat(result).hasSize(2);
	}

	@Test
	void toResponseList_returnsEmptyListForNull() {
		assertThat(mapper.toResponseList(null)).isEmpty();
	}

}
