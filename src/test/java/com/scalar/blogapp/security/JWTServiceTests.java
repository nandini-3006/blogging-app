package com.scalar.blogapp.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTServiceTests {

    private JWTService jwtService = new JWTService("your_super_secret_jwt_key_that_is_at_least_32_bytes_long_and_very_random", 60);

    @Test
    public void canCreateJwtFromUsername() {
        String token = jwtService.createJwt("testuser");
        assertNotNull(token);
    }
}