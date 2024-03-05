package com.example.demo.security.provider;

import org.springframework.security.core.Authentication;

/**
 * @author meow
 */
public class RefreshJwtAuthenticationProvider extends AbstractAuthenticationProvider{

    @Override
    protected Authentication getAuthentication(Authentication authentication) {
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
