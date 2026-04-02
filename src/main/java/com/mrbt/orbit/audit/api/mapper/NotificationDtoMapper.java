package com.mrbt.orbit.audit.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.api.mapper.GenericDtoMapper;
import com.mrbt.orbit.common.util.EnumUtils;
import com.mrbt.orbit.audit.api.response.NotificationResponse;
import com.mrbt.orbit.audit.core.model.Notification;

@Component
public class NotificationDtoMapper extends GenericDtoMapper<Void, NotificationResponse, Notification> {

	@Override
	public Notification toDomain(Void request) {
		throw new UnsupportedOperationException("Notifications are created internally, not from API requests");
	}

	@Override
	public NotificationResponse toResponse(Notification domain) {
		if (domain == null) {
			return null;
		}
		return NotificationResponse.builder().id(domain.getId()).userId(domain.getUserId())
				.type(EnumUtils.toStringOrNull(domain.getType())).title(domain.getTitle()).message(domain.getMessage())
				.channel(EnumUtils.toStringOrNull(domain.getChannel())).isRead(domain.getIsRead())
				.createdAt(domain.getCreatedAt()).build();
	}

}
