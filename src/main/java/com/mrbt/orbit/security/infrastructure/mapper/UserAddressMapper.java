package com.mrbt.orbit.security.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.infrastructure.entity.UserAddressEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;

@Component
public class UserAddressMapper extends AbstractNullSafeMapper<UserAddressEntity, UserAddress> {

	@Override
	public UserAddress toDomain(UserAddressEntity entity) {
		if (entity == null) {
			return null;
		}
		return UserAddress.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUser().getId()).label(entity.getLabel()).addressLine1(entity.getAddressLine1())
				.addressLine2(entity.getAddressLine2()).city(entity.getCity()).state(entity.getState())
				.postalCode(entity.getPostalCode()).countryCode(entity.getCountryCode())
				.isDefault(entity.getIsDefault()).build();
	}

	@Override
	public UserAddressEntity toEntity(UserAddress domain) {
		if (domain == null) {
			return null;
		}
		UserAddressEntity entity = new UserAddressEntity();
		entity.setId(domain.getId());
		entity.setLabel(domain.getLabel());
		entity.setAddressLine1(domain.getAddressLine1());
		entity.setAddressLine2(domain.getAddressLine2());
		entity.setCity(domain.getCity());
		entity.setState(domain.getState());
		entity.setPostalCode(domain.getPostalCode());
		entity.setCountryCode(domain.getCountryCode());
		entity.setIsDefault(domain.getIsDefault());

		UserEntity user = new UserEntity();
		user.setId(domain.getUserId());
		entity.setUser(user);

		return entity;
	}
}
