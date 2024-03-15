package com.gatewayservice.gatewayservice.config;

import com.gatewayservice.gatewayservice.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(MyUserDetailsService myUserDetailsService) {
        return myUserDetailsService;
    }

    @Bean
    @Order(1)
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, MyUserDetailsService myUserDetailsService) {
        return http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and() // Enable CORS support
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/users/register", "/api/users/welcome", "/api/auth/status").permitAll()
                        .pathMatchers("/catalogservice/api/books/download/**").permitAll()
                        .pathMatchers("/catalogservice/**").permitAll()
                        .pathMatchers("/storageservice/**").hasRole("ADMIN")
                )
                .formLogin(formLogin -> formLogin
                        .authenticationSuccessHandler((webFilterExchange, authentication) -> {
                            ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();
                            return redirectStrategy.sendRedirect(webFilterExchange.getExchange(), URI.create("http://localhost:5173/"));
                        })
                )
                .authenticationManager(authentication -> {
                    ReactiveAuthenticationManager authenticationManager = authenticationManagerBean(myUserDetailsService);
                    return authenticationManager.authenticate(authentication);
                })
                .build();
    }


    @Bean
    public ReactiveAuthenticationManager authenticationManagerBean(MyUserDetailsService myUserDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return authentication -> Mono.defer(() -> Mono.just(provider.authenticate(authentication)));
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedOriginPattern("http://localhost:5173");
        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
