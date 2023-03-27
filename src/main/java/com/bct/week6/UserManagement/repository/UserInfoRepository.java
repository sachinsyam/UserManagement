package com.bct.week6.UserManagement.repository;

import com.bct.week6.UserManagement.entity.UserInfo;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo>
    findByUsername(String username);
    UserInfo findById(Long id);

    UserInfo save(UserInfo userinfo);

    //write a query to update username

//    @Query(value="update user_info (username) values()")
//    UserInfo update2(UserInfo userInfo);

    @Transactional
    @Modifying
    @Query("update UserInfo set username= :updatedUsername  where id = :id")
    Integer updateUsername(String updatedUsername, int id);


  /*
    custom native query is created by using @Query to search for the owner_name field and shop_type filed.
    @Param annotation is used to bind method parameters to a query
     */

    //Custom query
    @Query(value = "select * from user_info where username like %:keyword% or name like  %:keyword%", nativeQuery = true)
    List<UserInfo> findByKeyword(@Param("keyword") String keyword);


}
