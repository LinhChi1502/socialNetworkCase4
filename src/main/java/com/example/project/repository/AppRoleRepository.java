package com.example.project.repository;

import com.example.project.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {
}
