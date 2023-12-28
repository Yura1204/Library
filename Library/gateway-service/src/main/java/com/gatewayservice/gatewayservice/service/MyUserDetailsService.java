package com.gatewayservice.gatewayservice.service;

import com.gatewayservice.gatewayservice.config.MyUserDetails;
import com.gatewayservice.gatewayservice.model.User;
import com.gatewayservice.gatewayservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Trying to load user by username: {}", username);
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            log.debug("User found: {}", user.get().getUsername());
            return user.map(MyUserDetails::new)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
        } else {
            log.debug("User not found with username: {}", username);
            throw new UsernameNotFoundException(username + " not found");
        }
    }

    public void logAuthenticationAttempt(String username, String password) {
        log.info("Attempting authentication with username: {}, password: {}", username, password);
    }
}
