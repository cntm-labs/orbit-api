package com.mrbt.orbit.integration.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.integration.infrastructure.entity.PlaidLinkEntity;

public interface PlaidLinkRepository extends JpaRepository<PlaidLinkEntity, UUID> {

}
