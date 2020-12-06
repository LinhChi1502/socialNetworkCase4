package com.example.project.service.friendship;

import com.example.project.model.Friendship;
import com.example.project.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService implements IFriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Override
    public Iterable<Friendship> findAll() {
        return friendshipRepository.findAll();

    }

    @Override
    public Friendship findById(Integer id) {
        return friendshipRepository.findById(id).get();
    }



    @Override
    public void save(Friendship model) {
        friendshipRepository.save(model);
    }

    @Override
    public void remove(Integer id) {
        friendshipRepository.deleteById(id);
    }
}
