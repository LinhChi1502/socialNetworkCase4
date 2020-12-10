package com.example.project.service.users;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.service.IService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAppUserService extends IService<AppUser> {
    AppUser getUserByName(String name);

    //Toan
    Iterable<AppUser> getAllByUserNameContaining(String keySearch);
    List<AppUser> searchAllFriendsByAppUser(AppUser user);
    List<AppUser>searchAllUserByNameAndGiveFlagToFriend(String keySearch);


}
