package com.bct.week6.UserManagement.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class UserController {
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome, this is not a secure page";
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/home")
    public String home(){
        return "Secure homepage after login";
    }
    @PreAuthorize("hasRole(‘ROLE_ADMIN')")
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //method level role auth
    //@PreAuthorize("hasRole(‘ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(){
        return "Admin dashboard";
    }

}
