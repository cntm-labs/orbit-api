package com.mrbt.orbit.security.core.port.in;

import java.util.List;
import java.util.UUID;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.security.core.model.UserAddress;
import com.mrbt.orbit.security.core.model.UserPreference;
import com.mrbt.orbit.security.core.model.NotificationPreference;

public interface UserProfileUseCase extends UseCase {
	// Address
	UserAddress addAddress(UserAddress address);
	List<UserAddress> getUserAddresses(UUID userId);
	UserAddress updateAddress(UserAddress address);
	void removeAddress(UUID addressId);

	// Preferences
	UserPreference getPreferences(UUID userId);
	UserPreference updatePreferences(UserPreference preference);

	// Notification Settings
	NotificationPreference getNotificationSettings(UUID userId);
	NotificationPreference updateNotificationSettings(NotificationPreference preference);
}
