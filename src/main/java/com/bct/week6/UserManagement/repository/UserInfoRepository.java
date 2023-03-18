package com.bct.week6.UserManagement.repository;

import com.bct.week6.UserManagement.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    /*
    custom native query is created by using @Query to search for the owner_name field and shop_type filed.
    @Param annotation is used to bind method parameters to a query
     */
    Optional<UserInfo> findByUsername(String username);
    UserInfo findById(Long id);

    UserInfo save(UserInfo userinfo);



    //Custom query
    @Query(value = "select * from user_info where username like %:keyword% or name like  %:keyword%", nativeQuery = true)
    List<UserInfo> findByKeyword(@Param("keyword") String keyword);

}
