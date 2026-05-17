package com.mrbt.orbit.security.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.port.out.UserPreferenceRepositoryPort;
import com.mrbt.orbit.security.infrastructure.mapper.UserPreferenceMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserPreferenceRepositoryAdapter implements UserPreferenceRepositoryPort {

	private final UserPreferenceRepository repository;
	private final UserPreferenceMapper mapper;

	@Override
	public UserPreference save(UserPreference preference) {
		return mapper.toDomain(repository.save(mapper.toEntity(preference)));
	}

	@Override
	public Optional<UserPreference> findByUserId(UUID userId) {
		return repository.findByUserId(userId).map(mapper::toDomain);
	}
}
