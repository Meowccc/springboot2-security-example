package com.example.demo.security;

import com.example.demo.entity.UserDO;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.cache.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author meow
 */
@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final CacheService cacheService;
    private final UserRepo userRepo;
    private final TokenUtil tokenUtil;
    private final ObjectMapper objectMapper;


    public String getSubject(final String token) {
        return tokenUtil.getSubject(token);
    }

    public UserPrincipal getCacheUserPrincipalByToken(final String token) {
        return objectMapper.convertValue(cacheService.getByKey(token), UserPrincipal.class);
    }

    public void cacheUserPrincipal(final String key, final UserPrincipal userPrincipal) {
        log.info("userPrincipal: {}", userPrincipal);
        cacheService.setByKey(key, userPrincipal);
    }

    public UserPrincipal loginByUsername(String username) {
        final UserDO userDO = userRepo.findByUsername(username).orElseThrow();
        return UserPrincipal.create(userDO.getId(), userDO.getUsername(), userDO.getPassword());
    }

    public UserPrincipal loginByUserId(final String userId) {
        UserDO userDO = userRepo.findById(userId).orElseThrow();
        return UserPrincipal.create(userDO.getId(), userDO.getUsername(), userDO.getPassword());
    }

    public LoginRes generateLoginRes(final UserPrincipal userPrincipal) {
        final String subject = userPrincipal.getUserId();
        return LoginRes.builder()
                .accessToken(tokenUtil.generateJwtToken(subject))
                .refreshToken(tokenUtil.generateJwtRefreshToken(subject))
                .build();
    }
}
