package com.mrbt.orbit.ledger.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.ledger.infrastructure.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {

}
