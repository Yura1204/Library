package com.gatewayservice.gatewayservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthStatusController {

    @GetMapping("/api/auth/status")
    public ResponseEntity<AuthStatus> getAuthStatus(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok(new AuthStatus(true));
        } else {
            return ResponseEntity.ok(new AuthStatus(false));
        }
    }

    private static class AuthStatus {
        private boolean authenticated;

        public AuthStatus(boolean authenticated) {
            this.authenticated = authenticated;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }
    }
}
