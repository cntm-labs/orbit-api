package com.mrbt.orbit.security.infrastructure.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.common.infrastructure.mapper.AbstractNullSafeMapper;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.infrastructure.entity.UserEntity;
import com.mrbt.orbit.security.infrastructure.entity.UserPreferenceEntity;

@Component
public class UserPreferenceMapper extends AbstractNullSafeMapper<UserPreferenceEntity, UserPreference> {

	@Override
	public UserPreference toDomain(UserPreferenceEntity entity) {
		if (entity == null) {
			return null;
		}
		return UserPreference.builder().id(entity.getId())
				.createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().atOffset(ZoneOffset.UTC) : null)
				.updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().atOffset(ZoneOffset.UTC) : null)
				.userId(entity.getUser().getId()).darkMode(entity.getDarkMode()).language(entity.getLanguage())
				.dateFormat(entity.getDateFormat()).numberFormat(entity.getNumberFormat())
				.biometricEnabled(entity.getBiometricEnabled()).lab3dCharts(entity.getLab3dCharts())
				.labAiSuggest(entity.getLabAiSuggest()).labSmartAlert(entity.getLabSmartAlert()).build();
	}

	@Override
	public UserPreferenceEntity toEntity(UserPreference domain) {
		if (domain == null) {
			return null;
		}
		UserPreferenceEntity entity = new UserPreferenceEntity();
		entity.setId(domain.getId());
		entity.setDarkMode(domain.getDarkMode());
		entity.setLanguage(domain.getLanguage());
		entity.setDateFormat(domain.getDateFormat());
		entity.setNumberFormat(domain.getNumberFormat());
		entity.setBiometricEnabled(domain.getBiometricEnabled());
		entity.setLab3dCharts(domain.getLab3dCharts());
		entity.setLabAiSuggest(domain.getLabAiSuggest());
		entity.setLabSmartAlert(domain.getLabSmartAlert());

		UserEntity user = new UserEntity();
		user.setId(domain.getUserId());
		entity.setUser(user);

		return entity;
	}
}
