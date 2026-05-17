package com.mrbt.orbit.security.api.mapper;

import org.springframework.stereotype.Component;

import com.mrbt.orbit.security.api.request.NotificationPreferenceRequest;
import com.mrbt.orbit.security.api.request.UserAddressRequest;
import com.mrbt.orbit.security.api.request.UserPreferenceRequest;
import com.mrbt.orbit.security.api.response.NotificationPreferenceResponse;
import com.mrbt.orbit.security.api.response.UserAddressResponse;
import com.mrbt.orbit.security.api.response.UserPreferenceResponse;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;

import java.util.UUID;

@Component
public class UserProfileDtoMapper {

	public UserAddress toDomain(UUID userId, UserAddressRequest request) {
		if (request == null) {
			return null;
		}
		return UserAddress.builder().id(request.id()).userId(userId).label(request.label())
				.addressLine1(request.addressLine1()).addressLine2(request.addressLine2()).city(request.city())
				.state(request.state()).postalCode(request.postalCode()).countryCode(request.countryCode())
				.isDefault(request.isDefault()).build();
	}

	public UserAddressResponse toResponse(UserAddress domain) {
		if (domain == null) {
			return null;
		}
		return new UserAddressResponse(domain.getId(), domain.getLabel(), domain.getAddressLine1(),
				domain.getAddressLine2(), domain.getCity(), domain.getState(), domain.getPostalCode(),
				domain.getCountryCode(), domain.getIsDefault());
	}

	public UserPreference toDomain(UUID userId, UserPreferenceRequest request) {
		if (request == null) {
			return null;
		}
		return UserPreference.builder().userId(userId).darkMode(request.darkMode()).language(request.language())
				.dateFormat(request.dateFormat()).numberFormat(request.numberFormat())
				.biometricEnabled(request.biometricEnabled()).lab3dCharts(request.lab3dCharts())
				.labAiSuggest(request.labAiSuggest()).labSmartAlert(request.labSmartAlert()).build();
	}

	public UserPreferenceResponse toResponse(UserPreference domain) {
		if (domain == null) {
			return null;
		}
		return new UserPreferenceResponse(domain.getDarkMode(), domain.getLanguage(), domain.getDateFormat(),
				domain.getNumberFormat(), domain.getBiometricEnabled(), domain.getLab3dCharts(),
				domain.getLabAiSuggest(), domain.getLabSmartAlert());
	}

	public NotificationPreference toDomain(UUID userId, NotificationPreferenceRequest request) {
		if (request == null) {
			return null;
		}
		return NotificationPreference.builder().userId(userId).emailEnabled(request.emailEnabled())
				.pushEnabled(request.pushEnabled()).budgetAlerts(request.budgetAlerts())
				.billReminders(request.billReminders()).largeTransactionThreshold(request.largeTransactionThreshold())
				.build();
	}

	public NotificationPreferenceResponse toResponse(NotificationPreference domain) {
		if (domain == null) {
			return null;
		}
		return new NotificationPreferenceResponse(domain.getEmailEnabled(), domain.getPushEnabled(),
				domain.getBudgetAlerts(), domain.getBillReminders(), domain.getLargeTransactionThreshold());
	}
}
