package com.mrbt.orbit.ledger.infrastructure.repository;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.ledger.core.model.Transaction;
import com.mrbt.orbit.ledger.core.port.out.TransactionRepositoryPort;
import com.mrbt.orbit.ledger.infrastructure.entity.AccountEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;
import com.mrbt.orbit.ledger.infrastructure.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
	public List<Transaction> findByAccountId(UUID accountId) {
		return springDataRepository.findByAccountId(accountId).stream().map(mapper::toDomain)
				.collect(Collectors.toList());
	}
}
