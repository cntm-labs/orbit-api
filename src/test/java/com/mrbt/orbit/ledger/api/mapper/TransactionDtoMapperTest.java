package com.mrbt.orbit.ledger.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.ledger.api.request.CreateTransactionRequest;
import com.mrbt.orbit.ledger.api.response.TransactionResponse;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;

class TransactionDtoMapperTest {

	private final TransactionDtoMapper mapper = new TransactionDtoMapper();

	@Test
	void toDomain_mapsAllFields() {
		UUID accountId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		Instant txDate = Instant.now();

		CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(accountId)
				.categoryId(categoryId).amount(new BigDecimal("100.50")).currencyCode("USD").description("Test")
				.transactionDate(txDate).build();

		Transaction result = mapper.toDomain(request);

		assertThat(result.getAccountId()).isEqualTo(accountId);
		assertThat(result.getCategoryId()).isEqualTo(categoryId);
		assertThat(result.getAmount()).isEqualByComparingTo(new BigDecimal("100.50"));
		assertThat(result.getCurrencyCode()).isEqualTo("USD");
		assertThat(result.getDescription()).isEqualTo("Test");
		assertThat(result.getTransactionDate()).isEqualTo(txDate);
	}

	@Test
	void toDomain_returnsNullForNull() {
		assertThat(mapper.toDomain(null)).isNull();
	}

	@Test
	void toResponse_mapsAllFields() {
		UUID id = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();
		OffsetDateTime createdAt = OffsetDateTime.now();

		Transaction domain = Transaction.builder().id(id).accountId(accountId).amount(BigDecimal.TEN)
				.currencyCode("THB").exchangeRate(BigDecimal.ONE).description("Desc").transactionDate(Instant.now())
				.status(TransactionStatus.COMPLETED).isReviewed(true).createdAt(createdAt).build();

		TransactionResponse result = mapper.toResponse(domain);

		assertThat(result.id()).isEqualTo(id);
		assertThat(result.accountId()).isEqualTo(accountId);
		assertThat(result.status()).isEqualTo("COMPLETED");
		assertThat(result.isReviewed()).isTrue();
		assertThat(result.createdAt()).isEqualTo(createdAt);
	}

	@Test
	void toResponse_returnsNullForNull() {
		assertThat(mapper.toResponse(null)).isNull();
	}

	@Test
	void toResponse_handlesNullStatus() {
		Transaction domain = Transaction.builder().amount(BigDecimal.ONE).build();
		TransactionResponse result = mapper.toResponse(domain);
		assertThat(result.status()).isNull();
	}

	@Test
	void toResponseList_convertsList() {
		Transaction t1 = Transaction.builder().description("T1").amount(BigDecimal.ONE).build();
		Transaction t2 = Transaction.builder().description("T2").amount(BigDecimal.TEN).build();
		List<TransactionResponse> result = mapper.toResponseList(List.of(t1, t2));
		assertThat(result).hasSize(2);
	}

	@Test
	void toResponseList_returnsEmptyListForNull() {
		assertThat(mapper.toResponseList(null)).isEmpty();
	}

}
