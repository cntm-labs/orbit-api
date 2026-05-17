package com.mrbt.orbit.security.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.port.out.NotificationPreferenceRepositoryPort;
import com.mrbt.orbit.security.infrastructure.mapper.NotificationPreferenceMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationPreferenceRepositoryAdapter implements NotificationPreferenceRepositoryPort {

	private final NotificationPreferenceRepository repository;
	private final NotificationPreferenceMapper mapper;

	@Override
	public NotificationPreference save(NotificationPreference preference) {
		return mapper.toDomain(repository.save(mapper.toEntity(preference)));
	}

	@Override
	public Optional<NotificationPreference> findByUserId(UUID userId) {
		return repository.findByUserId(userId).map(mapper::toDomain);
	}
}
