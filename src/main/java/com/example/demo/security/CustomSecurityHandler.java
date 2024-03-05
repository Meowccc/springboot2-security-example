package com.example.demo.security;

import com.example.demo.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

/**
 * @author meow
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomSecurityHandler {

    private final ObjectMapper objectMapper;

    private final AuthService authService;

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            log.info("[successHandler]");
            final UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final LoginRes loginRes = authService.generateLoginRes(userPrincipal);
            String userId = userPrincipal.getUserId();
            authService.cacheUserPrincipal(loginRes.getAccessToken(), userPrincipal);
            outputResponse(response, HttpStatus.OK, ResponseDTO.ok(loginRes));
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return (request, response, authentication) -> {
            log.info("[failureHandler]");
            outputResponse(response, HttpStatus.UNAUTHORIZED, ResponseDTO.error("AuthenticationFailureHandler"));
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.info("[authenticationEntryPoint]");
            outputResponse(response, HttpStatus.UNAUTHORIZED, ResponseDTO.error("AuthenticationEntryPoint"));
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            log.info("[accessDeniedHandler]");
            outputResponse(response, HttpStatus.FORBIDDEN, ResponseDTO.error("AccessDeniedHandler"));
        };
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {

        };
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (request, response, authentication) -> {
            log.info("[logoutSuccessHandler]");
            outputResponse(response, HttpStatus.OK, ResponseDTO.ok());
        };
    }

    @SneakyThrows
    private <T> void outputResponse(final HttpServletResponse response, final HttpStatus httpStatus, final T data) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter().print(objectMapper.writeValueAsString(data));
    }
}
