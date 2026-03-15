package com.mrbt.orbit.security.core.port.out;

import com.mrbt.orbit.security.core.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
	User save(User user);
	Optional<User> findByClerkUserId(String clerkUserId);
	Optional<User> findByEmail(String email);
	boolean existsByClerkUserId(String clerkUserId);
	boolean existsByEmail(String email);
}
