package com.example.project.repository;

import com.example.project.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser getAppUsersByUserName(String name);
}

