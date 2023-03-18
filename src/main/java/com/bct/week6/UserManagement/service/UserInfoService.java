package com.bct.week6.UserManagement.service;

import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;



    public List<UserInfo> loadAllUsers(){
        return  userInfoRepository.findAll();
    }
    public List<UserInfo> searchUsers(String keyword){
        return  userInfoRepository.findByKeyword(keyword);
    }

    public UserInfo getUser(Long id){
        return userInfoRepository.findById(id);
     }
    public void updateUser(UserInfo userInfo) {
         userInfoRepository.save(userInfo);

    }


    public void delete(int id) {
        userInfoRepository.deleteById(id);
    }
}
