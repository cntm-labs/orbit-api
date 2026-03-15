package com.mrbt.orbit.security.infrastructure.repository;

import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

	private final UserRepository springDataRepository;
	private final UserMapper mapper;

	@Override
	public User save(User user) {
		UserEntity entity = mapper.toEntity(user);
		UserEntity savedEntity = springDataRepository.save(entity);
		return mapper.toDomain(savedEntity);
	}

	@Override
	public Optional<User> findByClerkUserId(String clerkUserId) {
		return springDataRepository.findByClerkUserId(clerkUserId).map(mapper::toDomain);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return springDataRepository.findByEmail(email).map(mapper::toDomain);
	}

	@Override
	public boolean existsByClerkUserId(String clerkUserId) {
		return springDataRepository.existsByClerkUserId(clerkUserId);
	}

	@Override
	public boolean existsByEmail(String email) {
		return springDataRepository.existsByEmail(email);
	}
}
