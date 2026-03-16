package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class GetTransactionServiceTest {

	@Mock
	private TransactionRepositoryPort transactionRepositoryPort;

	@InjectMocks
	private GetTransactionService getTransactionService;

	@Test
	void getTransactionById_returnsTransaction() {
		UUID txId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(txId).amount(new BigDecimal("42.00")).build();

		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.of(tx));

		Optional<Transaction> result = getTransactionService.getTransactionById(txId);

		assertThat(result).isPresent();
		assertThat(result.get().getAmount()).isEqualByComparingTo("42.00");
	}

	@Test
	void getTransactionById_returnsEmptyWhenNotFound() {
		UUID txId = UUID.randomUUID();
		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.empty());

		assertThat(getTransactionService.getTransactionById(txId)).isEmpty();
	}

	@Test
	void getTransactionsByAccountId_returnsList() {
		UUID accountId = UUID.randomUUID();
		List<Transaction> txs = List.of(Transaction.builder().amount(new BigDecimal("10")).build(),
				Transaction.builder().amount(new BigDecimal("20")).build());

		when(transactionRepositoryPort.findByAccountId(accountId)).thenReturn(txs);

		List<Transaction> result = getTransactionService.getTransactionsByAccountId(accountId);

		assertThat(result).hasSize(2);
		verify(transactionRepositoryPort).findByAccountId(accountId);
	}

}
