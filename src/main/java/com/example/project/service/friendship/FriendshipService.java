package com.example.project.service.friendship;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public Iterable<AppUser> findUserFriendByOtherUser(AppUser user) {
        Iterable<Friendship> friendshipList = friendshipRepository.findAllByFriendStatusIsAndUser1IsOrUser2Is(1, user, user);
        List<AppUser> appUsers = new ArrayList<>();
        for (Friendship friendship : friendshipList
        ) {
            if (friendship.getUser1().getUserId() == user.getUserId()) {
                appUsers.add(friendship.getUser2());
            } else {
                appUsers.add(friendship.getUser1());
            }

        }
        return appUsers;
    }

    @Override
    public Iterable<Friendship> findAllByFriendStatusIsAndUser1IsOrUser2Is(int status, AppUser user1, AppUser user2) {
        return friendshipRepository.findAllByFriendStatusIsAndUser1IsOrUser2Is(status, user1, user2);
    }


}
