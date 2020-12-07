package com.example.project.service.users;

import com.example.project.model.AppRole;
import com.example.project.model.AppUser;
import com.example.project.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements IAppUserService, UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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


    //Toan
    @Override
    public Iterable<AppUser> getAppUserByUserNameContaining(String keySearch) {
        return appUserRepository.getAppUserByUserNameContaining(keySearch);
    }

    @Override
    public Iterable<AppUser> getUser1Friends(int id) {
       return appUserRepository.getUser1Friends(id);
    }

    @Override
    public Iterable<AppUser> getUser2Friends(int id) {
        return appUserRepository.getUser2Friends(id);
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


}
