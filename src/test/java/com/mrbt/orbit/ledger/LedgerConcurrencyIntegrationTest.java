package com.mrbt.orbit.ledger;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mrbt.orbit.ledger.core.model.Account;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.AccountStatus;
import com.mrbt.orbit.ledger.core.model.enums.AccountType;
import com.mrbt.orbit.ledger.core.port.in.CreateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.AccountRepositoryPort;

@SpringBootTest
@ActiveProfiles("test")
class LedgerConcurrencyIntegrationTest {

	@Autowired
	private CreateTransactionUseCase createTransactionUseCase;

	@Autowired
	private AccountRepositoryPort accountRepositoryPort;

	@Test
	void concurrentTransactions_shouldMaintainBalanceConsistency() throws Exception {
		// 1. Setup an account with 0 balance
		UUID userId = UUID.randomUUID();
		Account account = Account.builder().userId(userId).name("Concurrency Test Account").type(AccountType.BANK)
				.currencyCode("USD").currentBalance(BigDecimal.ZERO).status(AccountStatus.ACTIVE).build();

		Account savedAccount = accountRepositoryPort.save(account);
		UUID accountId = savedAccount.getId();

		// 2. Prepare 50 concurrent transactions of $10 each
		int numberOfTransactions = 50;
		BigDecimal amountPerTransaction = new BigDecimal("10.00");
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<CompletableFuture<Void>> futures = new ArrayList<>();

		for (int i = 0; i < numberOfTransactions; i++) {
			CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
				Transaction tx = Transaction.builder().accountId(accountId).amount(amountPerTransaction)
						.currencyCode("USD").description("Concurrent Tx").build();
				createTransactionUseCase.createTransaction(tx);
			}, executor);
			futures.add(future);
		}

		// 3. Wait for all transactions to complete
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		executor.shutdown();

		// 4. Verify final balance
		Account finalAccount = accountRepositoryPort.findById(accountId).orElseThrow();
		BigDecimal expectedBalance = amountPerTransaction.multiply(new BigDecimal(numberOfTransactions));

		// Use compareTo for BigDecimal comparison to ignore scale differences
		assertThat(finalAccount.getCurrentBalance().compareTo(expectedBalance))
				.as("Final balance should be exactly " + expectedBalance).isEqualTo(0);
	}
}
