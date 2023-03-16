package com.bct.week6.UserManagement.controller;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    UserRegistrationService userRegistrationService;
    @PostMapping("/add")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userRegistrationService.addUser(userInfo);
    }

}
