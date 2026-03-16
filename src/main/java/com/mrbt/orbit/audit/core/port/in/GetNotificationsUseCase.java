package com.mrbt.orbit.audit.core.port.in;

import java.util.UUID;

import com.mrbt.orbit.audit.core.model.Notification;
import com.mrbt.orbit.common.core.model.PageResult;
import com.mrbt.orbit.common.core.port.in.UseCase;

public interface GetNotificationsUseCase extends UseCase {

	PageResult<Notification> getNotificationsByUserId(UUID userId, int page, int size);

	long getUnreadCount(UUID userId);

}
