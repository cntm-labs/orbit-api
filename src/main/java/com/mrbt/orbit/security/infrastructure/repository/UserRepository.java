package com.mrbt.orbit.security.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

	Optional<UserEntity> findByClerkUserId(String clerkUserId);

	Optional<UserEntity> findByEmail(String email);

	boolean existsByClerkUserId(String clerkUserId);

	boolean existsByEmail(String email);

}
