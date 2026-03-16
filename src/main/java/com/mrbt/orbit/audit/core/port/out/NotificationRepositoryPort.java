package com.mrbt.orbit.audit.core.port.out;

import java.util.Optional;
import java.util.UUID;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.common.core.model.PageResult;

public interface NotificationRepositoryPort {

	Notification save(Notification notification);

	Optional<Notification> findById(UUID id);

	PageResult<Notification> findByUserId(UUID userId, int page, int size);

	long countUnreadByUserId(UUID userId);

	void markAsReadById(UUID id);

	void markAllAsReadByUserId(UUID userId);

}
