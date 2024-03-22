package com.phatdo.passwordmanager.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository repo;
    public void registerUser(User user) throws Exception {
        Optional<User> optUser = repo.findByUsername(user.getUsername());
        if (optUser.isEmpty())
            repo.save(user);
        else
            throw new Exception();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = repo.findByUsername(username);
        return optUser.map(user -> user).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
