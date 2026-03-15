package com.mrbt.orbit.ledger.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.ledger.infrastructure.entity.TransactionTagEntity;
import com.mrbt.orbit.ledger.infrastructure.entity.TransactionTagId;

public interface TransactionTagRepository extends JpaRepository<TransactionTagEntity, TransactionTagId> {

}
