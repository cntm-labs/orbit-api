package com.mrbt.orbit.security.core.port.out;

import java.util.Optional;
import java.util.UUID;

import com.mrbt.orbit.security.core.model.NotificationPreference;

public interface NotificationPreferenceRepositoryPort {
	NotificationPreference save(NotificationPreference preference);
	Optional<NotificationPreference> findByUserId(UUID userId);
}
