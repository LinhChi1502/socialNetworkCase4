package com.example.project.repository;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {
    Iterable<Friendship> findAllByFriendStatusIsAndUser1IsOrUser2Is(int status, AppUser user1, AppUser user2);

    //Toan-search friendship Link
    Iterable<Friendship>getAllByFriendStatusIsAndUser1Is(int status,AppUser user1);
    Iterable<Friendship>getAllByFriendStatusIsAndUser2Is(int status,AppUser user2);

}
