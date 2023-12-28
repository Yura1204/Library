package com.gatewayservice.gatewayservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    @GetMapping("/welcome")
    @Secured({})
    public String welcome(){
        return "Welcome to the unprotected page";
    }
}
