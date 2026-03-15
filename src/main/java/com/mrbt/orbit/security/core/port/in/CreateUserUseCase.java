package com.mrbt.orbit.security.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.security.core.model.User;

public interface CreateUserUseCase extends UseCase {
	User createUser(User user);
}
