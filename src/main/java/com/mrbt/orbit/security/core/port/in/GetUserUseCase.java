package com.mrbt.orbit.security.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.security.core.model.User;
import java.util.Optional;

public interface GetUserUseCase extends UseCase {
	Optional<User> getUserByClerkId(String clerkUserId);
}
