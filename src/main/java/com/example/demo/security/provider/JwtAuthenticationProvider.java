package com.example.demo.security.provider;

import com.example.demo.constant.WebConstant;
import com.example.demo.enums.GenericStatusEnum;
import com.example.demo.security.AuthService;
import com.example.demo.security.UserPrincipal;
import com.example.demo.security.authentication.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author meow
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider extends AbstractAuthenticationProvider {

    private final AuthService authService;

    @Override
    protected Authentication getAuthentication(Authentication authentication) {
        final Object credentials = authentication.getCredentials();
        final String token = Optional.ofNullable(credentials)
                .map(String::valueOf)
                .filter(StringUtils::isNotBlank)
                .filter(t -> t.length() > WebConstant.BEARER_PREFIX.length())
                .map(t -> t.substring(WebConstant.BEARER_PREFIX.length()).trim())
                .orElseThrow();

        UserPrincipal userPrincipal = authService.getCacheUserPrincipalByToken(token);

        if (userPrincipal == null) {
            String subject = authService.getSubject(token);
            // if token not found in cache, then get user info from auth-server/db
            userPrincipal = authService.loginByUserId(subject);
            // check user status
            if (userPrincipal.getStatus() == GenericStatusEnum.INACTIVE) {
                throw new BadCredentialsException("user is: INACTIVE");
            }
            // TODO
            authService.cacheUserPrincipal(subject, userPrincipal);
        }
        return new JwtAuthenticationToken(userPrincipal, null, List.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
