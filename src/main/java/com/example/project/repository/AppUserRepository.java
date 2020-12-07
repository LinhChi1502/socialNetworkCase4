package com.example.project.repository;

import com.example.project.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser getAppUsersByUserName(String name);
    //Toan
    Iterable<AppUser> getAppUserByUserNameContaining(String keySearch);
//    @Query(value = "select Friendship .user1 from Friendship where user2.userId=?1 and Friendship.friendStatus=1",nativeQuery = true)
//    List<AppUser> getUser1Friends(int id);
//    @Query(value = "select Friendship .user2 from Friendship where user1.userId=?1 and Friendship.friendStatus=1",nativeQuery = true)
//    List<AppUser> getUser2Friends(int id);


}

