package com.bct.week6.UserManagement.service;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserInfo userInfo){
        Optional<UserInfo> existingUser = userInfoRepository.findByUsername(userInfo.getUsername());
        if(existingUser.isPresent()){
            return "userExists";
        }
        else{
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            userInfoRepository.save(userInfo);
            //redirect to login page after registration
            return "login-page";
        }

    }

}
