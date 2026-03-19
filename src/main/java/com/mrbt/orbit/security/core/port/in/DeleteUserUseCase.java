package com.mrbt.orbit.security.core.port.in;

import com.mrbt.orbit.common.core.port.in.UseCase;
import java.util.UUID;

public interface DeleteUserUseCase extends UseCase {

	void deactivateUser(UUID userId);

}
