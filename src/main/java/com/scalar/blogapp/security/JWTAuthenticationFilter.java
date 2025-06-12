package com.scalar.blogapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationConverter authenticationConverter;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        this.authenticationManager = authenticationManager;
        this.authenticationConverter = authenticationConverter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            Authentication authenticationRequest = authenticationConverter.convert(request);

            if (authenticationRequest != null) {
                Authentication authenticatedResult = authenticationManager.authenticate(authenticationRequest);
                SecurityContextHolder.getContext().setAuthentication(authenticatedResult);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Authentication failed: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}