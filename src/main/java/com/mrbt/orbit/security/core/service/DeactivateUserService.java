package com.mrbt.orbit.security.core.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mrbt.orbit.common.exception.ResourceNotFoundException;
import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.DeleteUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeactivateUserService implements DeleteUserUseCase {

	private final UserRepositoryPort userRepositoryPort;

	@Override
	@Transactional
	public void deactivateUser(UUID userId) {
		User user = userRepositoryPort.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));
		user.deactivate();
		userRepositoryPort.save(user);
	}

}
