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
