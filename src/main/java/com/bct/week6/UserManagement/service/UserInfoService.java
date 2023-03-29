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


    //all
    public List<UserInfo> loadAllUsers(){
        return  userInfoRepository.findAll();
    }
    //search
    public List<UserInfo> searchUsers(String keyword){
        return  userInfoRepository.findByKeyword(keyword);
    }
    //findById
    public UserInfo getUser(Long id){
        return userInfoRepository.findById(id);
     }
     //save user`
    public void updateUser(UserInfo userInfo) { userInfoRepository.save(userInfo);  }

//    public int updateUser2(UserInfo user){
//      return userInfoRepository.updateUsername(user.getUsername(),user.getId());
//    }
    //delete user
    public void delete(int id) {
        userInfoRepository.deleteById(id);
    }
}
