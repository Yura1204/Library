package com.gatewayservice.gatewayservice.config;

import com.gatewayservice.gatewayservice.service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(MyUserDetailsService myUserDetailsService) {
        return myUserDetailsService;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, MyUserDetailsService myUserDetailsService) throws Exception {
        return http.csrf().disable()
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/users/register", "/api/users/welcome").permitAll()
                        .pathMatchers("/catalogservice/api/books/download/**").permitAll()
                        .pathMatchers("/catalogservice/**").permitAll()
                        .pathMatchers("/storageservice/**").hasRole("ADMIN")
                )
                .formLogin(Customizer.withDefaults())
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
