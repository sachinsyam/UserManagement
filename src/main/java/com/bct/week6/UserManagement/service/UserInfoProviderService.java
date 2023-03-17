package com.bct.week6.UserManagement.service;

import com.bct.week6.UserManagement.config.UserInfoToUserDetailsConversion;
import com.bct.week6.UserManagement.entity.UserInfo;
import com.bct.week6.UserManagement.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoProviderService implements UserDetailsService {
   @Autowired
    UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //Getting user info from db.
      Optional<UserInfo> userInfo =   userInfoRepository.findByUsername(username);
    //Converting user info to UserDetails so that it can be returned.
    return userInfo.map(UserInfoToUserDetailsConversion::new)
              .orElseThrow(()-> new UsernameNotFoundException("user not found:"+username));

    }
}
