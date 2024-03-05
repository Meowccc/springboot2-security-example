package com.example.demo.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author meow
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private final Object credentials;

    public JwtAuthenticationToken(Object credentials) {
        super(null);
        this.principal = null;
        this.credentials = credentials;
    }

    public JwtAuthenticationToken(Object credentials, Object principal) {
        super(null);
        this.credentials = credentials;
        this.principal = principal;
    }

    public JwtAuthenticationToken(Object credentials, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.credentials = credentials;
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
