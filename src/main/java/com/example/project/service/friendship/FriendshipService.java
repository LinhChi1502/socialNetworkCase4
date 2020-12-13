package com.example.project.service.friendship;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.repository.FriendshipRepository;
import com.example.project.service.users.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendshipService implements IFriendshipService {
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private AppUserService userService;

    @ModelAttribute
    private AppUser currentUser() {
        return userService.getCurrentUser();
    }

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
    public void sendFriendRequest(int beSendUserId) {
        AppUser beSendUser = userService.findById(beSendUserId);
        Friendship friendship = new Friendship();
        friendship.setActionUser(currentUser());
        friendship.setFriendStatus(0);
        if (beSendUser.getUserId() > currentUser().getUserId()) {
            friendship.setUser1(currentUser());
            friendship.setUser2(beSendUser);
        } else {
            friendship.setUser1(beSendUser);
            friendship.setUser2(currentUser());
        }
        friendshipRepository.save(friendship);
    }

    @Transactional
    public void deleteByUser1UserIdAndUser2UserId(int userId1, int userId2){
        friendshipRepository.removeFriendshipByUser1UserIdAndUser2UserId(userId1, userId2);
    }

    @Override
    public void acceptFriendRequest(AppUser user) {
        try{
            if (currentUser().getUserId()<user.getUserId()){
                Friendship friendship = friendshipRepository.getByUser1IsAndUser2Is(currentUser(), user);
                friendship.setFriendStatus(1);
                friendship.setActionUser(currentUser());
                friendshipRepository.save(friendship);
            }else
            {
                Friendship friendship = friendshipRepository.getByUser1IsAndUser2Is(user,currentUser());
                friendship.setFriendStatus(1);
                friendship.setActionUser(currentUser());
                friendshipRepository.save(friendship);
            }

        }catch (Exception e){

            Friendship friendship=new Friendship();
            if (currentUser().getUserId()<user.getUserId()){
                friendship.setUser1( currentUser());
                friendship.setUser2(user);
                friendship.setFriendStatus(1);
                friendship.setActionUser(currentUser());
            }else
            {
                friendship.setUser1(user);
                friendship.setUser2(currentUser());
                friendship.setFriendStatus(1);
                friendship.setActionUser(currentUser());
            }

        }
    }
}
