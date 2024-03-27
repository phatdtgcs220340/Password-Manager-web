package com.phatdo.passwordmanager.Entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repo;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
    }

    public void registerUser(User user) throws Exception {
        Optional<User> optUser = repo.findByUsername(user.getUsername());
        if (optUser.isEmpty())
            repo.save(user);
        else
            throw new Exception();
    }

}
