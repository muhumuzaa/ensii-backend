package com.ensiibackend.security;

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.context.annotation.Bean;

public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpHeaderSecurityFilter http) throws Exception{

    }
}
