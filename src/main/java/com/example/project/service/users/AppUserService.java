package com.example.project.service.users;

import com.example.project.model.AppRole;
import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.repository.AppUserRepository;
import com.example.project.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements IAppUserService, UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private FriendshipRepository friendshipRepository;

    @ModelAttribute
    private AppUser currentUser() {
        return this.getCurrentUser();
    }


    @Override
    public Iterable<AppUser> findAll() {
        Iterable<AppUser> users = appUserRepository.findAll();
        return users;
    }

    @Override
    public AppUser findById(Integer id) {
        AppUser user = appUserRepository.findById(id).get();

        return user;
    }

    @Override
    public void save(AppUser appUser) {
        AppRole appRole = new AppRole();
        appRole.setId(1);
        appUser.setRole(appRole);
        appUserRepository.save(appUser);
    }

    @Override
    public void remove(Integer id) {
        appUserRepository.deleteById(id);

    }

    @Override
    public AppUser getUserByName(String name) {
        return appUserRepository.getAppUsersByUserName(name);
    }


    // Toan
    @Override
    public List<AppUser> searchAllFriendsByAppUser(AppUser user) {
        Iterable<Friendship> listFriend2 = friendshipRepository.getAllByFriendStatusIsAndUser1Is(1, user);
        Iterable<Friendship> listFriend1 = friendshipRepository.getAllByFriendStatusIsAndUser2Is(1, user);

        List<AppUser> friendList = new ArrayList<>();

        for (Friendship friendship : listFriend2
        ) {
            friendList.add(friendship.getUser2());
        }
        for (Friendship friendship : listFriend1
        ) {
            friendList.add(friendship.getUser1());
        }
        return friendList;
    }


    @Override
    public Iterable<AppUser> getAllByUserNameContaining(String keySearch) {
        Iterable<AppUser> allByUserNameContaining = appUserRepository.getAllByUserNameContaining(keySearch);
        return allByUserNameContaining;
    }

    @Override
    public List<AppUser> searchAllUserByNameAndGiveFlagToFriend(String keySearch) {
        Iterable<AppUser> userSearchAll = this.getAllByUserNameContaining(keySearch);
        List<AppUser> listAllAppUser = new ArrayList<>();
        for (AppUser user : userSearchAll
        ) {
            user.setFlag(2);
            listAllAppUser.add(user);
        }

        AppUser currentUser = this.getCurrentUser();
        List<AppUser> friendUsers = this.searchAllFriendsByAppUser(currentUser);
        List<AppUser> pendingUsers = this.searchAllPendingFriendsByUser(currentUser);

        for (AppUser user : friendUsers
        ) {
            user.setFlag(1);
        }

        for (AppUser user : pendingUsers
        ) {
            user.setFlag(0);
        }

        listAllAppUser.remove(currentUser);

        return listAllAppUser;
    }

    @Override
    public List<AppUser> searchAllPendingFriendsByUser(AppUser user) {
        Iterable<Friendship> listOnePending = friendshipRepository.getAllByFriendStatusIsAndUser2Is(0, user);
        Iterable<Friendship> listTowPending = friendshipRepository.getAllByFriendStatusIsAndUser1Is(0, user);
        List<AppUser> pendingUsers = new ArrayList<>();
        for (Friendship friendship : listOnePending
        ) {
            pendingUsers.add(friendship.getUser1());
        }
        for (Friendship friendship : listTowPending
        ) {
            pendingUsers.add(friendship.getUser2());
        }
        return pendingUsers;
    }

    @Override
    public void removeFriendshipsByUser1IsAndUser2Is(int beRemoveFriendId) {


    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = this.getUserByName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getRole());
        UserDetails userDetails = new User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
        return userDetails;
    }

    public void signUpUser(AppUser appUser) {

        String encryptedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encryptedPassword);
        AppRole appRole = new AppRole();
        appRole.setId(1);
        appUser.setRole(appRole);

        appUserRepository.save(appUser);

    }

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        AppUser appUser = this.getUserByName(name);
        return appUser;
    }


}
