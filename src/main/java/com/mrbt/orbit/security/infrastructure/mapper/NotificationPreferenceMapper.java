package com.mrbt.orbit.security.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.infrastructure.entity.NotificationPreferenceEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

@Component
public class NotificationPreferenceMapper
		extends
			AbstractNullSafeMapper<NotificationPreferenceEntity, NotificationPreference> {

	@Override
	public NotificationPreference toDomain(NotificationPreferenceEntity entity) {
		if (entity == null) {
			return null;
		}
		return NotificationPreference.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUser().getId()).emailEnabled(entity.getEmailEnabled())
				.pushEnabled(entity.getPushEnabled()).budgetAlerts(entity.getBudgetAlerts())
				.billReminders(entity.getBillReminders())
				.largeTransactionThreshold(entity.getLargeTransactionThreshold()).build();
	}

	@Override
	public NotificationPreferenceEntity toEntity(NotificationPreference domain) {
		if (domain == null) {
			return null;
		}
		NotificationPreferenceEntity entity = new NotificationPreferenceEntity();
		entity.setId(domain.getId());
		entity.setEmailEnabled(domain.getEmailEnabled());
		entity.setPushEnabled(domain.getPushEnabled());
		entity.setBudgetAlerts(domain.getBudgetAlerts());
		entity.setBillReminders(domain.getBillReminders());
		entity.setLargeTransactionThreshold(domain.getLargeTransactionThreshold());

		UserEntity user = new UserEntity();
		user.setId(domain.getUserId());
		entity.setUser(user);

		return entity;
	}
}
