package com.mrbt.orbit.ledger.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.ledger.infrastructure.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

	Page<TransactionEntity> findByAccountId(UUID accountId, Pageable pageable);

}
