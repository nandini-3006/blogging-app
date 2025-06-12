package com.scalar.blogapp.security;

import com.scalar.blogapp.users.UserEntity; // Corrected import based on your UserEntity package
import com.scalar.blogapp.users.UserService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections; // Added import for Collections.emptyList()

@Component
public class JWTAuthenticationManager implements AuthenticationManager {

    private final JWTService jwtService;
    private final UserService userService;

    public JWTAuthenticationManager(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof JWTAuthentication)) {
            return null;
        }

        JWTAuthentication jwtAuthentication = (JWTAuthentication) authentication;
        String jwt = (String) jwtAuthentication.getCredentials();

        if (jwt == null) {
            throw new BadCredentialsException("JWT token is missing.");
        }

        if (!jwtService.isValid(jwt)) {
            throw new BadCredentialsException("Invalid or expired JWT token.");
        }

        String username; // Changed from Long userId to String username
        try {
            username = jwtService.retrieveUsername(jwt); // Changed to retrieveUsername
        } catch (Exception e) {
            throw new BadCredentialsException("Could not retrieve username from token.", e);
        }

        UserEntity userEntity = userService.getUser(username); // Changed to getUser(username)

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found for the given token username.");
        }


        return new JWTAuthentication(jwt, userEntity, Collections.emptyList());
    }
}