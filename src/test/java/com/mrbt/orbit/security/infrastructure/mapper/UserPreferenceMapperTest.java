package com.mrbt.orbit.security.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserPreferenceEntity;

class UserPreferenceMapperTest {

	private final UserPreferenceMapper mapper = new UserPreferenceMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		UserPreferenceEntity entity = new UserPreferenceEntity();
		entity.setId(UUID.randomUUID());
		entity.setUser(user);
		entity.setDarkMode(true);

		UserPreference domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getDarkMode()).isTrue();
	}
}
