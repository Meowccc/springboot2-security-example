package com.example.demo.security.provider;

import com.example.authjar.auth.JwtAuthenticationProvider;
import com.example.demo.security.AuthService;
import com.example.demo.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author meow
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAuthenticationProvider extends AbstractAuthenticationProvider {

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    @Override
    protected Authentication getAuthentication(Authentication authentication) {
        final String username = (String) authentication.getPrincipal();
        final String plainPassword = (String) authentication.getCredentials();
        final UserPrincipal userPrincipal = authService.loginByUsername(username);
        if (!this.passwordEncoder.matches(plainPassword, userPrincipal.getPassword())) {
            throw new BadCredentialsException("login failed");
        }
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());
        loginToken.setDetails(userPrincipal);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, List.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
