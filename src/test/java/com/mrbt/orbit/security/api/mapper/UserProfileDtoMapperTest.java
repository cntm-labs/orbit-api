package com.mrbt.orbit.security.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mrbt.orbit.security.api.request.NotificationPreferenceRequest;
import com.mrbt.orbit.security.api.request.UserAddressRequest;
import com.mrbt.orbit.security.api.request.UserPreferenceRequest;
import com.mrbt.orbit.security.api.response.NotificationPreferenceResponse;
import com.mrbt.orbit.security.api.response.UserAddressResponse;
import com.mrbt.orbit.security.api.response.UserPreferenceResponse;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;

class UserProfileDtoMapperTest {

	private final UserProfileDtoMapper mapper = new UserProfileDtoMapper();

	@Test
	void addressMapping_ShouldWork() {
		UUID userId = UUID.randomUUID();
		UserAddressRequest request = new UserAddressRequest(null, "Home", "Line 1", null, "City", null, "12345", "US",
				true);

		UserAddress domain = mapper.toDomain(userId, request);
		assertThat(domain.getUserId()).isEqualTo(userId);
		assertThat(domain.getLabel()).isEqualTo("Home");

		UserAddressResponse response = mapper.toResponse(domain);
		assertThat(response.label()).isEqualTo("Home");
	}

	@Test
	void preferenceMapping_ShouldWork() {
		UUID userId = UUID.randomUUID();
		UserPreferenceRequest request = new UserPreferenceRequest(true, "en", null, null, true, false, false, false);

		UserPreference domain = mapper.toDomain(userId, request);
		assertThat(domain.getUserId()).isEqualTo(userId);
		assertThat(domain.getDarkMode()).isTrue();

		UserPreferenceResponse response = mapper.toResponse(domain);
		assertThat(response.darkMode()).isTrue();
	}

	@Test
	void notificationMapping_ShouldWork() {
		UUID userId = UUID.randomUUID();
		NotificationPreferenceRequest request = new NotificationPreferenceRequest(true, true, true, true,
				new BigDecimal("100.00"));

		NotificationPreference domain = mapper.toDomain(userId, request);
		assertThat(domain.getUserId()).isEqualTo(userId);
		assertThat(domain.getEmailEnabled()).isTrue();

		NotificationPreferenceResponse response = mapper.toResponse(domain);
		assertThat(response.emailEnabled()).isTrue();
	}
}
