package com.scalar.blogapp.security;

import com.scalar.blogapp.users.UserEntity; // Corrected import based on your UserEntity package
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections; // Added import

public class JWTAuthentication extends AbstractAuthenticationToken {

    private final String jwt;
    private UserEntity userEntity;

    public JWTAuthentication(String jwt) {
        super(null);
        this.jwt = jwt;
        setAuthenticated(false);
    }

    public JWTAuthentication(String jwt, UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(authorities); // Pass authorities to the super constructor
        this.jwt = jwt;
        this.userEntity = userEntity;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return super.getAuthorities(); // Relies on authorities passed to super(authorities)
    }
}