package com.mrbt.orbit.security.core.service;

import com.mrbt.orbit.security.core.model.User;
import com.mrbt.orbit.security.core.port.in.GetUserUseCase;
import com.mrbt.orbit.security.core.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

	private final UserRepositoryPort userRepositoryPort;

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUserByClerkId(String clerkUserId) {
		return userRepositoryPort.findByClerkUserId(clerkUserId);
	}
}
