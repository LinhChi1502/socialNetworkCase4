package com.example.project.service.users;

import com.example.project.model.Users;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements IUsersService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Users> findAll() {
        Iterable<Users> users = userRepository.findAll();
        return users;
    }

    @Override
    public Users findById(Integer id) {
        Users user = userRepository.findById(id).get();

        return user;


    }

    @Override
    public void update(Users model) {

    }

    @Override
    public void save(Users model) {

    }

    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);

    }
}
