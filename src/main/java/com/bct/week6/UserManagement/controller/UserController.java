package com.bct.week6.UserManagement.controller;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.repository.UserInfoRepository;
import com.bct.week6.UserManagement.service.UserInfoService;
import com.bct.week6.UserManagement.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/app")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    private String currentUser;

    //getting current username
    public ResponseEntity<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(username);
    }



    //  UserInfo userInfo;
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }




    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/home")
    public String home(Model model){
        //Getting user info from db.
        String username = getCurrentUsername().getBody();
        model.addAttribute("username", username);
        return "home-page";
    }





    @PreAuthorize("hasAuthority(‘ROLE_USER')")
    @GetMapping("/test")
    public String test(){
        return "test";
    }






    @GetMapping("/admin")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')") //method level role auth
    //@PreAuthorize("hasRole(‘ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Model model){
        List<UserInfo> users = userInfoService.loadAllUsers();
        model.addAttribute("users",users);

        return "adminDashboard";
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String searchUser(){
        userInfoService.searchUsers("kk");
        return "admin";
    }





    @Autowired
    UserRegistrationService userRegistrationService;
    @PostMapping("/add")
    public String addNewUser( @ModelAttribute  UserInfo userInfo){
        System.out.println("test");
        return userRegistrationService.addUser(userInfo);
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute UserInfo userInfo){
        userInfo.setRoles("ROLE_USER");
        System.out.println(userInfo.toString());
        return userRegistrationService.addUser(userInfo);
    }




}
