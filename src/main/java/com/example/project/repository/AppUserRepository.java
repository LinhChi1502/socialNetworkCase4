package com.example.project.repository;

import com.example.project.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser getAppUsersByUserName(String name);
    //Toan
    Iterable<AppUser> getAllByUserNameContaining(String keySearch);
}

