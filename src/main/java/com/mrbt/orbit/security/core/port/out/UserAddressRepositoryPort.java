package com.mrbt.orbit.security.core.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.mrbt.orbit.security.core.model.UserAddress;

public interface UserAddressRepositoryPort {
	UserAddress save(UserAddress address);
	Optional<UserAddress> findById(UUID id);
	List<UserAddress> findByUserId(UUID userId);
	void deleteById(UUID id);
}
