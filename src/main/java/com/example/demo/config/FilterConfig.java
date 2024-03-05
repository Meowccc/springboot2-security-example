package com.example.demo.config;

import com.example.demo.filter.LoggingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author meow
 */
@Slf4j
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilterRegistration() {
        log.info("FilterConfig loggingFilterRegistration");
        FilterRegistrationBean<LoggingFilter> registration = new FilterRegistrationBean<>();
        LoggingFilter loggingFilter = new LoggingFilter();
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeQueryString(true);
        registration.setFilter(loggingFilter);
        registration.addUrlPatterns("/*");
        registration.setName("loggingFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
