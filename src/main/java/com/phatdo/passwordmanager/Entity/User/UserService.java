package com.phatdo.passwordmanager.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.phatdo.passwordmanager.Entity.Application.Application;
import com.phatdo.passwordmanager.Entity.Application.ApplicationRepository;
import com.phatdo.passwordmanager.Entity.UserApplication.UserApplication;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private UserRepository repo;
    private ApplicationRepository applicationRepository;
    @Autowired
    public UserService(UserRepository userRepository, ApplicationRepository applicationRepository) {
        this.repo = userRepository;
        this.applicationRepository = applicationRepository;
    }
    public void registerUser(User user) throws Exception {
        Optional<User> optUser = repo.findByUsername(user.getUsername());
        if (optUser.isEmpty())
            repo.save(user);
        else
            throw new Exception();
    }

    public void addApplication(User user, Application application) {
        // add new application to application list
        user.getApplications().add(application);
        UserApplication userApplication = new UserApplication(user, application);
        // update new assoc instance to assoc table
        user.getUserApplications().add(userApplication);
        application.getUserApplications().add(userApplication);
        applicationRepository.save(application);
    }
    public Set<Application> listApplication(User user) {
        return user.getApplications();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = repo.findByUsername(username);
        return optUser.map(user -> user).orElseThrow(() -> new UsernameNotFoundException(username));
    }

}
