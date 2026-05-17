package com.mrbt.orbit.security.core.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.security.core.model.NotificationPreference;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.port.in.UserProfileUseCase;
import com.mrbt.orbit.security.core.port.out.NotificationPreferenceRepositoryPort;
import com.mrbt.orbit.security.core.port.out.UserAddressRepositoryPort;
import com.mrbt.orbit.security.core.port.out.UserPreferenceRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService implements UserProfileUseCase {

	private final UserAddressRepositoryPort addressRepositoryPort;
	private final UserPreferenceRepositoryPort preferenceRepositoryPort;
	private final NotificationPreferenceRepositoryPort notificationRepositoryPort;

	@Override
	@Transactional
	public UserAddress addAddress(UserAddress address) {
		return addressRepositoryPort.save(address);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserAddress> getUserAddresses(UUID userId) {
		return addressRepositoryPort.findByUserId(userId);
	}

	@Override
	@Transactional
	public UserAddress updateAddress(UserAddress address) {
		addressRepositoryPort.findById(address.getId())
				.orElseThrow(() -> new ResourceNotFoundException("UserAddress", "id", address.getId()));
		return addressRepositoryPort.save(address);
	}

	@Override
	@Transactional
	public void removeAddress(UUID addressId) {
		addressRepositoryPort.deleteById(addressId);
	}

	@Override
	@Transactional(readOnly = true)
	public UserPreference getPreferences(UUID userId) {
		return preferenceRepositoryPort.findByUserId(userId)
				.orElseGet(() -> UserPreference.builder().userId(userId).darkMode(false).language("en").build());
	}

	@Override
	@Transactional
	public UserPreference updatePreferences(UserPreference preference) {
		return preferenceRepositoryPort.save(preference);
	}

	@Override
	@Transactional(readOnly = true)
	public NotificationPreference getNotificationSettings(UUID userId) {
		return notificationRepositoryPort.findByUserId(userId).orElseGet(
				() -> NotificationPreference.builder().userId(userId).emailEnabled(true).pushEnabled(true).build());
	}

	@Override
	@Transactional
	public NotificationPreference updateNotificationSettings(NotificationPreference preference) {
		return notificationRepositoryPort.save(preference);
	}
}
