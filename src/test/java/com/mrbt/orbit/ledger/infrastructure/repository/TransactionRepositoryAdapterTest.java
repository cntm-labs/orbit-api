package com.mrbt.orbit.ledger.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;
import com.mrbt.orbit.ledger.infrastructure.mapper.TransactionMapper;

@ExtendWith(MockitoExtension.class)
class TransactionRepositoryAdapterTest {

	@Mock
	private TransactionRepository springDataRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Mock
	private TransactionMapper mapper;

	@InjectMocks
	private TransactionRepositoryAdapter adapter;

	@Test
	void save_withCategory_convertsAndPersists() {
		UUID accountId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		Transaction domain = Transaction.builder().accountId(accountId).categoryId(categoryId)
				.amount(new BigDecimal("50")).build();
		AccountEntity accountEntity = new AccountEntity();
		CategoryEntity categoryEntity = new CategoryEntity();
		TransactionEntity entity = new TransactionEntity();
		TransactionEntity savedEntity = new TransactionEntity();
		Transaction expected = Transaction.builder().id(UUID.randomUUID()).build();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
		when(mapper.toEntity(domain, accountEntity, categoryEntity)).thenReturn(entity);
		when(springDataRepository.save(entity)).thenReturn(savedEntity);
		when(mapper.toDomain(savedEntity)).thenReturn(expected);

		assertThat(adapter.save(domain)).isEqualTo(expected);
	}

	@Test
	void save_withoutCategory_convertsAndPersists() {
		UUID accountId = UUID.randomUUID();
		Transaction domain = Transaction.builder().accountId(accountId).amount(new BigDecimal("50")).build();
		AccountEntity accountEntity = new AccountEntity();
		TransactionEntity entity = new TransactionEntity();
		TransactionEntity savedEntity = new TransactionEntity();
		Transaction expected = Transaction.builder().id(UUID.randomUUID()).build();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountEntity));
		when(mapper.toEntity(eq(domain), eq(accountEntity), any())).thenReturn(entity);
		when(springDataRepository.save(entity)).thenReturn(savedEntity);
		when(mapper.toDomain(savedEntity)).thenReturn(expected);

		assertThat(adapter.save(domain)).isEqualTo(expected);
	}

	@Test
	void save_accountNotFound_throwsException() {
		UUID accountId = UUID.randomUUID();
		Transaction domain = Transaction.builder().accountId(accountId).build();

		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> adapter.save(domain)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void save_categoryNotFound_throwsException() {
		UUID accountId = UUID.randomUUID();
		UUID categoryId = UUID.randomUUID();
		Transaction domain = Transaction.builder().accountId(accountId).categoryId(categoryId).build();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(new AccountEntity()));
		when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> adapter.save(domain)).isInstanceOf(ResourceNotFoundException.class);
	}

	@Test
	void findById_returnsTransaction() {
		UUID txId = UUID.randomUUID();
		TransactionEntity entity = new TransactionEntity();
		Transaction expected = Transaction.builder().id(txId).build();

		when(springDataRepository.findById(txId)).thenReturn(Optional.of(entity));
		when(mapper.toDomain(entity)).thenReturn(expected);

		assertThat(adapter.findById(txId)).isPresent().contains(expected);
	}

	@Test
	void findById_returnsEmptyWhenNotFound() {
		UUID txId = UUID.randomUUID();
		when(springDataRepository.findById(txId)).thenReturn(Optional.empty());

		assertThat(adapter.findById(txId)).isEmpty();
	}

	@Test
	void findByAccountId_returnsList() {
		UUID accountId = UUID.randomUUID();
		TransactionEntity entity = new TransactionEntity();
		Transaction mapped = Transaction.builder().id(UUID.randomUUID()).build();

		when(springDataRepository.findByAccountId(accountId)).thenReturn(List.of(entity));
		when(mapper.toDomain(entity)).thenReturn(mapped);

		assertThat(adapter.findByAccountId(accountId)).hasSize(1);
	}

}
