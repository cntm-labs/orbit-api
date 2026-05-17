package com.mrbt.orbit.security.api.response;

import java.util.UUID;

public record AuthResponse(String token, long expiresIn, UUID userId, String email) {
}
