package com.mrbt.orbit.security.core.service;

import com.mrbt.orbit.common.exception.DuplicateResourceException;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.model.enums.UserStatus;
import com.mrbt.orbit.security.core.port.in.CreateUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {

	private final UserRepositoryPort userRepositoryPort;

	@Override
	@Transactional
	public User createUser(User user) {
		if (userRepositoryPort.existsByClerkUserId(user.getClerkUserId())) {
			throw new DuplicateResourceException("User", "Clerk ID", user.getClerkUserId());
		}
		if (userRepositoryPort.existsByEmail(user.getEmail())) {
			throw new DuplicateResourceException("User", "Email", user.getEmail());
		}
		// Set default values for new users
		if (user.getStatus() == null) {
			user.setStatus(UserStatus.ACTIVE);
		}
		if (user.getBaseCurrency() == null) {
			user.setBaseCurrency("USD");
		}

		return userRepositoryPort.save(user);
	}
}
