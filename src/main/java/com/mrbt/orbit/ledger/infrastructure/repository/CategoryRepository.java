package com.mrbt.orbit.ledger.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.ledger.infrastructure.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

}
