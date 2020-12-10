package com.example.project.service.friendship;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.service.IService;

public interface IFriendshipService extends IService<Friendship> {
    Iterable<Friendship> findAllByFriendStatusIsAndUser1IsOrUser2Is(int status, AppUser user1, AppUser user2);





}
