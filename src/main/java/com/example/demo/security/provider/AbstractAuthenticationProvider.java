package com.example.demo.security.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author meow
 */
@Slf4j
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.isAuthenticated()) {
            return authentication;
        } else {
            try {
                return this.getAuthentication(authentication);
            } catch (AuthenticationException e) {
                throw e;
            } catch (ExpiredJwtException e) {
                throw new BadCredentialsException("ExpiredJwtException");
            } catch (JwtException e) {
                throw new BadCredentialsException("JwtException");
            } catch (Exception e) {
                throw new InternalAuthenticationServiceException("Exception");
            }
        }
    }

    protected abstract Authentication getAuthentication(final Authentication authentication);
}
