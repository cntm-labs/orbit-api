package com.mrbt.orbit.crypto.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.crypto.infrastructure.entity.CryptoAssetEntity;

public interface CryptoAssetRepository extends JpaRepository<CryptoAssetEntity, UUID> {

}
