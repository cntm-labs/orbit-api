package com.mrbt.orbit.ledger.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.api.request.CreateRecurringTransactionRequest;
import com.mrbt.orbit.ledger.api.response.RecurringTransactionResponse;
import com.mrbt.orbit.ledger.core.model.RecurringTransaction;
import com.mrbt.orbit.ledger.core.model.enums.Frequency;
import com.mrbt.orbit.ledger.core.model.enums.RecurringTransactionStatus;

class RecurringTransactionDtoMapperTest {

	private final RecurringTransactionDtoMapper mapper = new RecurringTransactionDtoMapper();

	@Test
	void toDomain_mapsAllFields() {
		UUID userId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		LocalDate startDate = LocalDate.of(2026, 4, 1);

		CreateRecurringTransactionRequest request = CreateRecurringTransactionRequest.builder().userId(userId)
				.accountId(accountId).categoryId(categoryId).amount(new BigDecimal("500.00")).currencyCode("THB")
				.description("Monthly rent").frequency(Frequency.MONTHLY).startDate(startDate).autoConfirm(true)
				.build();

		RecurringTransaction result = mapper.toDomain(request);

		assertThat(result.getUserId()).isEqualTo(userId);
		assertThat(result.getAccountId()).isEqualTo(accountId);
		assertThat(result.getCategoryId()).isEqualTo(categoryId);
		assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("500.00"));
		assertThat(result.getCurrencyCode()).isEqualTo("THB");
		assertThat(result.getDescription()).isEqualTo("Monthly rent");
		assertThat(result.getFrequency()).isEqualTo(Frequency.MONTHLY);
		assertThat(result.getNextOccurrence()).isEqualTo(startDate);
		assertThat(result.getAutoConfirm()).isTrue();
	}

	@Test
	void toDomain_returnsNullForNull() {
		assertThat(mapper.toDomain(null)).isNull();
	}

	@Test
	void toResponse_mapsAllFields() {
		UUID id = UUID.randomUUID();
		OffsetDateTime now = OffsetDateTime.now();

		RecurringTransaction domain = RecurringTransaction.builder().id(id).userId(UUID.randomUUID())
				.accountId(UUID.randomUUID()).categoryId(UUID.randomUUID()).amount(BigDecimal.valueOf(200))
				.currencyCode("USD").description("Subscription").frequency(Frequency.WEEKLY)
				.nextOccurrence(LocalDate.of(2026, 5, 1)).autoConfirm(false).status(RecurringTransactionStatus.ACTIVE)
				.createdAt(now).updatedAt(now).build();

		RecurringTransactionResponse result = mapper.toResponse(domain);

		assertThat(result.id()).isEqualTo(id);
		assertThat(result.frequency()).isEqualTo("WEEKLY");
		assertThat(result.status()).isEqualTo("ACTIVE");
		assertThat(result.autoConfirm()).isFalse();
		assertThat(result.createdAt()).isEqualTo(now);
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullEnums() {
		RecurringTransaction domain = RecurringTransaction.builder().amount(BigDecimal.ONE).build();
		RecurringTransactionResponse result = mapper.toResponse(domain);
		assertThat(result.frequency()).isNull();
		assertThat(result.status()).isNull();
	}

	@Test
	void toResponseList_convertsList() {
		RecurringTransaction r1 = RecurringTransaction.builder().description("R1").amount(BigDecimal.ONE).build();
		RecurringTransaction r2 = RecurringTransaction.builder().description("R2").amount(BigDecimal.TEN).build();
		List<RecurringTransactionResponse> result = mapper.toResponseList(List.of(r1, r2));
		assertThat(result).hasSize(2);
	}

	@Test
	void toResponseList_returnsEmptyListForNull() {
		assertThat(mapper.toResponseList(null)).isEmpty();
	}

}
