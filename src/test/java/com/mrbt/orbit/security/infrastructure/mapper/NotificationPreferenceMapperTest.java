package com.mrbt.orbit.security.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.infrastructure.entity.NotificationPreferenceEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

class NotificationPreferenceMapperTest {

	private final NotificationPreferenceMapper mapper = new NotificationPreferenceMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		NotificationPreferenceEntity entity = new NotificationPreferenceEntity();
		entity.setId(UUID.randomUUID());
		entity.setUser(user);
		entity.setEmailEnabled(true);

		NotificationPreference domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getEmailEnabled()).isTrue();
	}
}
