package com.example.authService.services;

import com.example.authService.DataAccess.UserRepo;
import com.example.authService.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    public UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }


    public User getUserByName(String username) {
        return userRepo.findByUsername(username);
    }

    public void createUser(User user) {
        userRepo.save(user);
    }
}
