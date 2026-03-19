package com.mrbt.orbit.ledger.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UpdateTransactionServiceTest {

	@Mock
	private TransactionRepositoryPort transactionRepositoryPort;

	@Mock
	private AccountRepositoryPort accountRepositoryPort;

	@InjectMocks
	private UpdateTransactionService updateTransactionService;

	@Test
	void updateTransaction_updatesDescription() {
		UUID txId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(txId).accountId(UUID.randomUUID()).amount(new BigDecimal("50"))
				.status(TransactionStatus.COMPLETED).description("Old").build();

		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.of(tx));
		when(transactionRepositoryPort.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

		Transaction result = updateTransactionService.updateTransaction(txId, "New Description", null, null);

		assertThat(result.getDescription()).isEqualTo("New Description");
		verify(transactionRepositoryPort).save(tx);
	}

	@Test
	void updateTransaction_throwsWhenNotFound() {
		UUID txId = UUID.randomUUID();
		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateTransactionService.updateTransaction(txId, "desc", null, null))
				.isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void voidTransaction_reversesBalanceForCompleted() {
		UUID txId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();
		BigDecimal amount = new BigDecimal("100.00");
		Transaction tx = Transaction.builder().id(txId).accountId(accountId).amount(amount)
				.status(TransactionStatus.COMPLETED).build();

		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.of(tx));
		when(transactionRepositoryPort.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

		updateTransactionService.voidTransaction(txId);

		assertThat(tx.getStatus()).isEqualTo(TransactionStatus.VOIDED);
		verify(accountRepositoryPort).updateBalance(eq(accountId), eq(amount.negate()));
		verify(transactionRepositoryPort).save(tx);
	}

	@Test
	void voidTransaction_doesNotReverseBalanceForPending() {
		UUID txId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(txId).accountId(accountId).amount(new BigDecimal("50"))
				.status(TransactionStatus.PENDING).build();

		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.of(tx));
		when(transactionRepositoryPort.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

		updateTransactionService.voidTransaction(txId);

		assertThat(tx.getStatus()).isEqualTo(TransactionStatus.VOIDED);
		verify(accountRepositoryPort, never()).updateBalance(any(), any());
	}

	@Test
	void voidTransaction_throwsWhenNotFound() {
		UUID txId = UUID.randomUUID();
		when(transactionRepositoryPort.findById(txId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> updateTransactionService.voidTransaction(txId))
				.isInstanceOf(ResourceNotFoundException.class);
	}

}
