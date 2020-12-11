package com.example.project.service.friendship;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.service.IService;

public interface IFriendshipService extends IService<Friendship> {
    void sendFriendRequest(int beSendUserId);

}
