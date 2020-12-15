package com.example.project.repository;

import com.example.project.model.AppUser;
import com.example.project.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> getNotificationsByUser(AppUser user);
}
