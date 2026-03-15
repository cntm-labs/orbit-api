package com.mrbt.orbit.crypto.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.crypto.infrastructure.entity.CryptoPortfolioSnapshotEntity;

public interface CryptoPortfolioSnapshotRepository extends JpaRepository<CryptoPortfolioSnapshotEntity, UUID> {

}
