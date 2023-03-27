package com.bct.week6.UserManagement.controller;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.service.UserInfoService;
import com.bct.week6.UserManagement.service.UserRegistrationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/app")
public class UserController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
        String username = getCurrentUsername().getBody();
        model.addAttribute("username", username);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        System.out.println("USER ADMIN:"+hasUserRole);

        model.addAttribute("isAdmin",hasUserRole);


        return "home-page";
    }





    @PreAuthorize("hasAuthority(‘ROLE_USER')")
    @GetMapping("/test")
    public String test(){
        return "test";
    }






    @GetMapping("/admin")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')") //method level role auth
    //@PreAuthorize("hasRole(‘ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin(Model model, String keyword){
        List<UserInfo> users;
        if(keyword == null){
            users = userInfoService.loadAllUsers();
        }
        else{
            users = userInfoService.searchUsers(keyword);
            System.out.println(users);
        }

        model.addAttribute("users",users);

        return "adminDashboard";
    }

    @GetMapping("/admin2")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')") //method level role auth
    //@PreAuthorize("hasRole(‘ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin2(Model model, String keyword){
        List<UserInfo> users;
        if(keyword == null){
            users = userInfoService.loadAllUsers();
        }
        else{
            users = userInfoService.searchUsers(keyword);
            System.out.println(users);
        }

        model.addAttribute("users",users);

        return "adminDashboard1";
    }


    //To view user.
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String viewUser(@PathVariable Long id, Model model){

        UserInfo user = userInfoService.getUser(id);

        model.addAttribute("id",id);
        model.addAttribute("name",user.getName());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("role",user.getRoles());


        return "viewUser";

    }
    @GetMapping("/admin/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUserById(@PathVariable("id") int id){
        System.out.println("delete user:"+id);
        userInfoService.delete(id);
        return "redirect:/app/admin";
    }


    //Update User
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("updateUser")
    public String updateUser(@ModelAttribute UserInfo updatedUser){
        UserInfo oldUser = userInfoService.getUser((long) updatedUser.getId());
        if(updatedUser.getPassword().isEmpty()){
            updatedUser.setPassword(oldUser.getPassword());
        }
        else{
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

        }

        userInfoService.updateUser(updatedUser);

        return "redirect:/app/admin/"+updatedUser.getId();
        //return "admin";

    }

    //update username
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/updateUsername")
    public Integer updateUsername(@RequestBody UserInfo userInfo){
        userInfoService.updateUser2(userInfo);
        return  userInfoService.updateUser2(userInfo);
    }



    //delete uer
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute UserInfo userInfo){
        userInfoService.delete(userInfo.getId());
        return "redirect:/app/admin";
    }



    //view crete user page
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/createUser")
    public String createUser(){
        return "createUser";
    }



    //Create user from admin dashboard
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/createUser")
    public String createNewUserFromAdminDashboard(@ModelAttribute UserInfo userInfo){
        if(userRegistrationService.addUser(userInfo) =="userExists" ){
            return "userExists";
        }
        else{
            return "redirect:/app/admin/"+userInfo.getId();
        }

    }

//    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String searchUser(String keyword){
//        userInfoService.searchUsers(keyword);
//
//        return "admin";
//    }


@GetMapping("/404")
public String notFound(){
        return "404";
}

    @GetMapping("/userExists")
    public String userExists(){
        return "user-already-exists";
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
        System.out.println(userInfo);
        return userRegistrationService.addUser(userInfo);
    }

//    public String getAllUser(){
//        List <Integer> list = new List<>;
//
//        list.add(1);
//        list.add(2);
//
//
//    }





}
