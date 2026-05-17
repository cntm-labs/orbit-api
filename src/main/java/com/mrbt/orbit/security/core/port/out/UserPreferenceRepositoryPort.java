package com.mrbt.orbit.security.core.port.out;

import java.util.Optional;
import java.util.UUID;

import com.mrbt.orbit.security.core.model.UserPreference;

public interface UserPreferenceRepositoryPort {
	UserPreference save(UserPreference preference);
	Optional<UserPreference> findByUserId(UUID userId);
}
