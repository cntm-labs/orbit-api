# Pagination (Transactions) Implementation Plan

> **For Claude:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task.

**Goal:** Add offset-based pagination to `GET /api/v1/transactions/account/{accountId}` — Notifications are already paginated.

**Architecture:** `Pageable` stays in `infrastructure/` only. Core layer receives `int page, int size`, returns `PageResult<T>`. Controller caps size at 100. Default sort: `transactionDate DESC`.

**Tech Stack:** Spring Boot 4.0.3, Java 25, Spring Data JPA, JUnit 5, Mockito

---

### Task 1: Update TransactionRepository

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepository.java`

**Step 1: Replace the list method with a pageable variant**

Replace the current file content with:
```java
package com.mrbt.orbit.ledger.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

	Page<TransactionEntity> findByAccount_Id(UUID accountId, Pageable pageable);

}
```

**Step 2: Apply formatting**

Run: `./mvnw spotless:apply -q`

---

### Task 2: Update TransactionRepositoryPort

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/core/port/out/TransactionRepositoryPort.java`

**Step 1: Change findByAccountId signature**

Replace file content with:
```java
package com.mrbt.orbit.ledger.core.port.out;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryPort {
	Transaction save(Transaction transaction);
	Optional<Transaction> findById(UUID id);
	PageResult<Transaction> findByAccountId(UUID accountId, int page, int size);
}
```

**Step 2: Apply formatting**

Run: `./mvnw spotless:apply -q`

---

### Task 3: Update TransactionRepositoryAdapter

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepositoryAdapter.java`

**Step 1: Replace findByAccountId implementation**

Replace the full file content with:
```java
package com.mrbt.orbit.ledger.infrastructure.repository;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;
import com.mrbt.orbit.ledger.infrastructure.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

	private final TransactionRepository springDataRepository;
	private final AccountRepository accountRepository;
	private final CategoryRepository categoryRepository;
	private final TransactionMapper mapper;

	@Override
	public Transaction save(Transaction transaction) {
		AccountEntity account = accountRepository.findById(transaction.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account", "ID", transaction.getAccountId()));

		CategoryEntity category = null;
		if (transaction.getCategoryId() != null) {
			category = categoryRepository.findById(transaction.getCategoryId())
					.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", transaction.getCategoryId()));
		}

		TransactionEntity entity = mapper.toEntity(transaction, account, category);
		TransactionEntity savedEntity = springDataRepository.save(entity);
		return mapper.toDomain(savedEntity);
	}

	@Override
	public Optional<Transaction> findById(UUID id) {
		return springDataRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public PageResult<Transaction> findByAccountId(UUID accountId, int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("transactionDate").descending());
		Page<TransactionEntity> entityPage = springDataRepository.findByAccount_Id(accountId, pageRequest);

		return new PageResult<>(entityPage.getContent().stream().map(mapper::toDomain).toList(),
				entityPage.getTotalElements(), entityPage.getTotalPages(), entityPage.getNumber(),
				entityPage.getSize());
	}
}
```

**Step 2: Apply formatting**

Run: `./mvnw spotless:apply -q`

---

### Task 4: Update GetTransactionUseCase and GetTransactionService

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/core/port/in/GetTransactionUseCase.java`
- Modify: `src/main/java/com/mrbt/orbit/ledger/core/service/GetTransactionService.java`

**Step 1: Update use case interface**

Replace `GetTransactionUseCase.java` content:
```java
package com.mrbt.orbit.ledger.core.port.in;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.ledger.core.model.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface GetTransactionUseCase extends UseCase {
	Optional<Transaction> getTransactionById(UUID transactionId);
	PageResult<Transaction> getTransactionsByAccountId(UUID accountId, int page, int size);
}
```

**Step 2: Update service implementation**

Replace `GetTransactionService.java` content:
```java
package com.mrbt.orbit.ledger.core.service;

import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.in.GetTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTransactionService implements GetTransactionUseCase {

	private final TransactionRepositoryPort transactionRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<Transaction> getTransactionById(UUID transactionId) {
		return transactionRepositoryPort.findById(transactionId);
	}

	@Override
	@Transactional(readOnly = true)
	public PageResult<Transaction> getTransactionsByAccountId(UUID accountId, int page, int size) {
		return transactionRepositoryPort.findByAccountId(accountId, page, size);
	}
}
```

**Step 3: Apply formatting**

Run: `./mvnw spotless:apply -q`

---

### Task 5: Update TransactionController

**Files:**
- Modify: `src/main/java/com/mrbt/orbit/ledger/api/TransactionController.java`

**Step 1: Add page/size params and update return type**

Replace the `getTransactionsByAccountId` method (keep other methods unchanged):

```java
@GetMapping("/account/{accountId}")
@Operation(summary = "Get transactions for an account", description = "Retrieves the paginated ledger history for a specific account, newest first")
public ResponseEntity<ApiResponse<PageResult<TransactionResponse>>> getTransactionsByAccountId(
        @PathVariable UUID accountId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
    int cappedSize = Math.min(size, 100);
    PageResult<Transaction> result = getTransactionUseCase.getTransactionsByAccountId(accountId, page, cappedSize);
    PageResult<TransactionResponse> responses = new PageResult<>(
            transactionMapper.toResponseList(result.content()),
            result.totalElements(), result.totalPages(), result.page(), result.size());

    return ResponseEntity.ok(ApiResponse.success(responses));
}
```

Also add this import at the top of the file:
```java
import com.mrbt.orbit.common.core.model.PageResult;
```

And remove this import (no longer needed for this method):
```java
import java.util.List;
```

**Step 2: Apply formatting**

Run: `./mvnw spotless:apply -q`

---

### Task 6: Update GetTransactionServiceTest

**Files:**
- Modify: `src/test/java/com/mrbt/orbit/ledger/core/service/GetTransactionServiceTest.java`

**Step 1: Replace the list-based test with paginated version**

Replace full file content:
```java
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

import com.mrbt.orbit.common.core.model.PageResult;
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
	void getTransactionsByAccountId_returnsPageResult() {
		UUID accountId = UUID.randomUUID();
		List<Transaction> txs = List.of(Transaction.builder().amount(new BigDecimal("10")).build(),
				Transaction.builder().amount(new BigDecimal("20")).build());
		PageResult<Transaction> page = new PageResult<>(txs, 2L, 1, 0, 20);

		when(transactionRepositoryPort.findByAccountId(accountId, 0, 20)).thenReturn(page);

		PageResult<Transaction> result = getTransactionService.getTransactionsByAccountId(accountId, 0, 20);

		assertThat(result.content()).hasSize(2);
		assertThat(result.totalElements()).isEqualTo(2L);
		assertThat(result.page()).isEqualTo(0);
		assertThat(result.size()).isEqualTo(20);
		verify(transactionRepositoryPort).findByAccountId(accountId, 0, 20);
	}

}
```

**Step 2: Run the service test**

Run: `./mvnw test -Dtest=GetTransactionServiceTest`

Expected: BUILD SUCCESS, 3 tests pass.

---

### Task 7: Update TransactionControllerTest

**Files:**
- Modify: `src/test/java/com/mrbt/orbit/ledger/api/TransactionControllerTest.java`

**Step 1: Update the account transactions test to use PageResult**

Replace the `getTransactionsByAccountId_returns200` test method and update the mock:

Replace full file content:
```java
package com.mrbt.orbit.ledger.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.ledger.api.request.CreateTransactionRequest;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.model.enums.TransactionStatus;
import com.mrbt.orbit.ledger.core.port.in.CreateTransactionUseCase;
import com.mrbt.orbit.ledger.core.port.in.GetTransactionUseCase;
import com.mrbt.orbit.ledger.infrastructure.mapper.TransactionMapper;

@WebMvcTest(TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TransactionMapper.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockitoBean
	private CreateTransactionUseCase createTransactionUseCase;

	@MockitoBean
	private GetTransactionUseCase getTransactionUseCase;

	@Test
	void createTransaction_returns201() throws Exception {
		UUID txId = UUID.randomUUID();
		UUID accountId = UUID.randomUUID();

		when(createTransactionUseCase.createTransaction(any(Transaction.class))).thenAnswer(inv -> {
			Transaction t = inv.getArgument(0);
			t.setId(txId);
			t.setStatus(TransactionStatus.COMPLETED);
			t.setIsReviewed(false);
			t.setExchangeRate(BigDecimal.ONE);
			t.setTransactionDate(Instant.now());
			return t;
		});

		CreateTransactionRequest request = CreateTransactionRequest.builder().accountId(accountId)
				.amount(new BigDecimal("50.00")).currencyCode("USD").description("Lunch").build();

		mockMvc.perform(post("/api/v1/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.data.description").value("Lunch"));
	}

	@Test
	void getTransactionById_returns200() throws Exception {
		UUID txId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(txId).accountId(UUID.randomUUID()).amount(new BigDecimal("42.00"))
				.status(TransactionStatus.COMPLETED).isReviewed(false).build();

		when(getTransactionUseCase.getTransactionById(txId)).thenReturn(Optional.of(tx));

		mockMvc.perform(get("/api/v1/transactions/{id}", txId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(txId.toString()));
	}

	@Test
	void getTransactionById_returns404WhenNotFound() throws Exception {
		UUID txId = UUID.randomUUID();
		when(getTransactionUseCase.getTransactionById(txId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/transactions/{id}", txId)).andExpect(status().isNotFound());
	}

	@Test
	void getTransactionsByAccountId_returnsPage() throws Exception {
		UUID accountId = UUID.randomUUID();
		Transaction tx = Transaction.builder().id(UUID.randomUUID()).accountId(accountId)
				.amount(new BigDecimal("10")).status(TransactionStatus.COMPLETED).build();
		PageResult<Transaction> page = new PageResult<>(List.of(tx), 1L, 1, 0, 20);

		when(getTransactionUseCase.getTransactionsByAccountId(eq(accountId), anyInt(), anyInt())).thenReturn(page);

		mockMvc.perform(get("/api/v1/transactions/account/{accountId}", accountId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.data.content").isArray())
				.andExpect(jsonPath("$.data.totalElements").value(1))
				.andExpect(jsonPath("$.data.page").value(0))
				.andExpect(jsonPath("$.data.size").value(20));
	}

	@Test
	void getTransactionsByAccountId_capsSizeAt100() throws Exception {
		UUID accountId = UUID.randomUUID();
		PageResult<Transaction> page = new PageResult<>(List.of(), 0L, 0, 0, 100);

		when(getTransactionUseCase.getTransactionsByAccountId(eq(accountId), eq(0), eq(100))).thenReturn(page);

		mockMvc.perform(get("/api/v1/transactions/account/{accountId}?size=999", accountId))
				.andExpect(status().isOk());
	}

}
```

**Step 2: Run the controller test**

Run: `./mvnw test -Dtest=TransactionControllerTest`

Expected: BUILD SUCCESS, 5 tests pass.

---

### Task 8: Run full verification

**Step 1: Run all tests**

Run: `./mvnw test`

Expected: BUILD SUCCESS. All tests pass. No compilation errors.

**Step 2: Check formatting**

Run: `./mvnw spotless:check`

Expected: BUILD SUCCESS. No formatting violations.

**Step 3: Commit**

```bash
git add \
  src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepository.java \
  src/main/java/com/mrbt/orbit/ledger/core/port/out/TransactionRepositoryPort.java \
  src/main/java/com/mrbt/orbit/ledger/infrastructure/repository/TransactionRepositoryAdapter.java \
  src/main/java/com/mrbt/orbit/ledger/core/port/in/GetTransactionUseCase.java \
  src/main/java/com/mrbt/orbit/ledger/core/service/GetTransactionService.java \
  src/main/java/com/mrbt/orbit/ledger/api/TransactionController.java \
  src/test/java/com/mrbt/orbit/ledger/core/service/GetTransactionServiceTest.java \
  src/test/java/com/mrbt/orbit/ledger/api/TransactionControllerTest.java
git commit -m "feat(ledger): add offset pagination to transactions endpoint"
```
