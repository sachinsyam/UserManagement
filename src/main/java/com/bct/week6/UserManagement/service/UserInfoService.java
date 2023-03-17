package com.bct.week6.UserManagement.service;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<UserInfo> loadAllUsers(){
        return  userInfoRepository.findAll();
    }
    public Optional<UserInfo> searchUsers(String text){
        return  userInfoRepository.findByUsername(text);
    }

}
