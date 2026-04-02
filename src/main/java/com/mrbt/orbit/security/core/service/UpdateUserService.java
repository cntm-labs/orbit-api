package com.mrbt.orbit.security.core.service;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.UpdateUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

	private final UserRepositoryPort userRepositoryPort;

	@Override
	@Transactional
	public User updateUser(UUID userId, String firstName, String lastName, String baseCurrency, String timezone) {
		User user = userRepositoryPort.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		if (firstName != null)
			user.setFirstName(firstName);
		if (lastName != null)
			user.setLastName(lastName);
		if (baseCurrency != null)
			user.setBaseCurrency(baseCurrency);
		if (timezone != null)
			user.setTimezone(timezone);
		return userRepositoryPort.save(user);
	}

}
