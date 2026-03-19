package com.mrbt.orbit.security.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import com.mrbt.orbit.security.core.model.User;
import java.util.UUID;

public interface UpdateUserUseCase extends UseCase {

	User updateUser(UUID userId, String firstName, String lastName, String baseCurrency, String timezone);

}
