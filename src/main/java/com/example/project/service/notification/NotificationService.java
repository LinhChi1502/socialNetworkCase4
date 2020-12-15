package com.example.project.service.notification;

import com.example.project.model.AppUser;
import com.example.project.model.Notification;
import com.example.project.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService{

    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public Iterable<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Integer id) {
        return notificationRepository.findById(id).get();
    }

    @Override
    public void save(Notification model) {
        notificationRepository.save(model);
    }

    @Override
    public void remove(Integer id) {
        notificationRepository.deleteById(id);
    }

    public List<Notification> getNotificationsByUser(AppUser user){
        return notificationRepository.getNotificationsByUser(user);
    }
}
