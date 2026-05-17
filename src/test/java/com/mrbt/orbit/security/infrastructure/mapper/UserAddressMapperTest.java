package com.mrbt.orbit.security.infrastructure.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.infrastructure.entity.UserAddressEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

class UserAddressMapperTest {

	private final UserAddressMapper mapper = new UserAddressMapper();

	@Test
	void toDomain_ShouldMapCorrectly() {
		UserEntity user = new UserEntity();
		user.setId(UUID.randomUUID());
		UserAddressEntity entity = new UserAddressEntity();
		entity.setId(UUID.randomUUID());
		entity.setUser(user);
		entity.setLabel("Home");

		UserAddress domain = mapper.toDomain(entity);

		assertThat(domain).isNotNull();
		assertThat(domain.getLabel()).isEqualTo(entity.getLabel());
		assertThat(domain.getUserId()).isEqualTo(user.getId());
	}
}
