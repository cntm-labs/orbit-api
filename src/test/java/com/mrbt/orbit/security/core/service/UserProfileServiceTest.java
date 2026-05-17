package com.mrbt.orbit.security.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.port.out.NotificationPreferenceRepositoryPort;
import com.mrbt.orbit.security.core.port.out.UserAddressRepositoryPort;
import com.mrbt.orbit.security.core.port.out.UserPreferenceRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

	@Mock
	private UserAddressRepositoryPort addressRepositoryPort;
	@Mock
	private UserPreferenceRepositoryPort preferenceRepositoryPort;
	@Mock
	private NotificationPreferenceRepositoryPort notificationRepositoryPort;

	@InjectMocks
	private UserProfileService userProfileService;

	@Test
	void addAddress_ShouldSaveAddress() {
		// Given
		UserAddress address = UserAddress.builder().userId(UUID.randomUUID()).label("Home").build();
		when(addressRepositoryPort.save(any(UserAddress.class))).thenReturn(address);

		// When
		UserAddress result = userProfileService.addAddress(address);

		// Then
		assertThat(result).isNotNull();
		verify(addressRepositoryPort).save(address);
	}

	@Test
	void getPreferences_ShouldReturnPreferences() {
		// Given
		UUID userId = UUID.randomUUID();
		UserPreference preference = UserPreference.builder().userId(userId).darkMode(true).build();
		when(preferenceRepositoryPort.findByUserId(userId)).thenReturn(Optional.of(preference));

		// When
		UserPreference result = userProfileService.getPreferences(userId);

		// Then
		assertThat(result.getDarkMode()).isTrue();
		verify(preferenceRepositoryPort).findByUserId(userId);
	}

	@Test
	void updatePreferences_ShouldSaveAndReturn() {
		UserPreference pref = UserPreference.builder().userId(UUID.randomUUID()).darkMode(true).build();
		when(preferenceRepositoryPort.save(any(UserPreference.class))).thenReturn(pref);

		UserPreference result = userProfileService.updatePreferences(pref);

		assertThat(result.getDarkMode()).isTrue();
		verify(preferenceRepositoryPort).save(pref);
	}

	@Test
	void updateNotificationSettings_ShouldSaveAndReturn() {
		NotificationPreference pref = NotificationPreference.builder().userId(UUID.randomUUID()).emailEnabled(true)
				.build();
		when(notificationRepositoryPort.save(any(NotificationPreference.class))).thenReturn(pref);

		NotificationPreference result = userProfileService.updateNotificationSettings(pref);

		assertThat(result.getEmailEnabled()).isTrue();
		verify(notificationRepositoryPort).save(pref);
	}
}
