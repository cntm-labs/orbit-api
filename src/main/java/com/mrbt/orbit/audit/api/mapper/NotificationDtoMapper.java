package com.mrbt.orbit.audit.api.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.audit.api.response.NotificationResponse;
import com.mrbt.orbit.audit.core.model.Notification;

@Component
public class NotificationDtoMapper {

	public NotificationResponse toResponse(Notification domain) {
		if (domain == null) {
			return null;
		}

		return NotificationResponse.builder().id(domain.getId()).userId(domain.getUserId())
				.type(domain.getType() != null ? domain.getType().name() : null).title(domain.getTitle())
				.message(domain.getMessage()).channel(domain.getChannel() != null ? domain.getChannel().name() : null)
				.isRead(domain.getIsRead()).createdAt(domain.getCreatedAt()).build();
	}

	public List<NotificationResponse> toResponseList(List<Notification> domains) {
		if (domains == null) {
			return null;
		}
		return domains.stream().map(this::toResponse).toList();
	}

}
