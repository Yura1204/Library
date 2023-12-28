package com.gatewayservice.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http.csrf(Customizer.withDefaults())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/users/register", "/api/users/welcome").permitAll()
                        .pathMatchers("/catalogservice/api/books/download/**").authenticated()
                        .pathMatchers("/catalogservice/**").permitAll()
                        .pathMatchers("/storageservice/**").authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
