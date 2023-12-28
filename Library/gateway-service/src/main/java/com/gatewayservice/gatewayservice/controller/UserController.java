package com.gatewayservice.gatewayservice.controller;

import com.gatewayservice.gatewayservice.model.User;
import com.gatewayservice.gatewayservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @GetMapping("/welcome")
    @Secured({})
    public String welcome(){
        return "Welcome to the unprotected page";
    }

    @PostMapping("/register")
    public String addUser(@RequestBody User user) {
        userService.addUser(user);
        return "User is saved";
    }
}
