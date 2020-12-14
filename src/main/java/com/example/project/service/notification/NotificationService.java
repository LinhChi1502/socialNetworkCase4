package com.example.project.service.notification;

import com.example.project.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements INotificationService{
    @Override
    public Iterable<Notification> findAll() {
        return null;
    }

    @Override
    public Notification findById(Integer id) {
        return null;
    }

    @Override
    public void save(Notification model) {

    }

    @Override
    public void remove(Integer id) {

    }
}
